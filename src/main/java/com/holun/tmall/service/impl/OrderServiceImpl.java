package com.holun.tmall.service.impl;

import com.holun.tmall.entity.*;
import com.holun.tmall.mapper.OrderMapper;
import com.holun.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderMapper orderMapper;
    private UserService userService;
    private OrderItemService orderItemService;
    private ProductService productService;
    private ReviewService reviewService;

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderItemService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public int addOrder(Order order) {
        return orderMapper.insertSelective(order);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    @Override
    public float addOrderAndUpdateOrderItem(Order order, List<OrderItem> orderItems) {
        addOrder(order);

        float total = 0;
        for (OrderItem orderItem : orderItems) {
            orderItem.setOid(order.getId());
            orderItemService.updateOrderItem(orderItem);
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
        }

        return total;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    @Override
    public float addOrderAndOrderItem(Order order, OrderItem orderItem) {
        addOrder(order);

        orderItem.setOid(order.getId());
        orderItemService.addOrderItem(orderItem);

        return orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
    }

    @Override
    public int deleteOrderById(int id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateOrder(Order order) {
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    @Override
    public int updateOrderAndOrderItemAndProductStock(Order order) {
        //修改订单
        updateOrder(order);

        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.setPaymentStatus("yes");
            //修改订单项
            orderItemService.updateOrderItem(orderItem);

            Product product = orderItem.getProduct();
            if (product.getStock() - orderItem.getNumber() >= 0)
                product.setStock(product.getStock() - orderItem.getNumber());
            else
                product.setStock(0);
            //修改产品库存
            productService.updateProduct(product);
        }

        return 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    @Override
    public int updateOrderAndOrderItemAndAddReview(Order order, OrderItem orderItem, Review review) {
        reviewService.addReview(review);

        orderItem.setReviewStatus("yes");
        orderItemService.updateOrderItem(orderItem);

        boolean flag = true;
        order = queryOrderById(order.getId());
        List<OrderItem> orderItems = order.getOrderItems();
        if (!orderItems.isEmpty()) {
            for (OrderItem item : orderItems) {
                if ("no".equals(item.getReviewStatus())) {
                    flag = false;
                    break;
                }
            }
        }
        //当订单中包含的所有订单项对应的产品都评价后，将订单状态设置为已完成
        if (flag) {
            order.setStatus(OrderService.finish);
            updateOrder(order);
        }

        return 0;
    }

    @Override
    public Order queryOrderById(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        fill(order);

        return order;
    }

    @Override
    public List<Order> list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("uid asc,id desc");
        List<Order> orders = orderMapper.selectByExample(example);
        //为orders集合中的所有order对象的user属性、orderItems属性、total属性、totalNumber属性注入值
        fill(orders);

        return orders;
    }

    @Override
    public List<Order> list(int uid, String excludedStatus) {
        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
        example.setOrderByClause("id desc");
        List<Order> orders = orderMapper.selectByExample(example);
        fill(orders);

        return orders;
    }

    @Override
    public void fill(List<Order> orders) {
        for (Order order : orders)
            fill(order);
    }

    @Override
    public void fill(Order order) {
        //查询下当前订单的用户
        User user = userService.queryUserById(order.getUid());
        //根据订单id，查询该订单包含多少个订单项
        List<OrderItem> orderItems = orderItemService.queryOrderItemByOid(order.getId());

        float total = 0;
        int totalNumber = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
            totalNumber += orderItem.getNumber();
        }

        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotal(total);
        order.setTotalNumber(totalNumber);
    }

}
