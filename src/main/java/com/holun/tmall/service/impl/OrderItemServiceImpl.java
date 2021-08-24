package com.holun.tmall.service.impl;

import com.holun.tmall.entity.OrderItem;
import com.holun.tmall.entity.OrderItemExample;
import com.holun.tmall.entity.Product;
import com.holun.tmall.mapper.OrderItemMapper;
import com.holun.tmall.service.OrderItemService;
import com.holun.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemMapper orderItemMapper;
    private ProductService productService;

    @Autowired
    public void setOrderItemMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public int addOrderItem(OrderItem orderItem) {
        return orderItemMapper.insertSelective(orderItem);
    }

    @Override
    public void addProductToCart(int pid, int number, int uid) {
        List<OrderItem> orderItems = listByUid(uid);
        boolean found = false;

        //如果加入购物车的产品，购物车中已存在这个产品对应的OrderItem，并且还没有生成订单，那么就应该在购物车中对应的OrderItem基础上，调整数量即可
        if (!orderItems.isEmpty()) {
            for (OrderItem orderItem : orderItems) {
                if (pid == orderItem.getPid()) {
                    orderItem.setNumber(orderItem.getNumber() + number);
                    updateOrderItem(orderItem);
                    found = true;
                    break;
                }
            }
        }

        //如果加入购物车的产品，购物车中不存在这个产品对应的OrderItem,那么就新增一个订单项OrderItem
        if (!found) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUid(uid);
            orderItem.setPid(pid);
            orderItem.setNumber(number);
            addOrderItem(orderItem);
        }
    }

    @Override
    public int deleteOrderItemById(int id) {
        return orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateOrderItem(OrderItem orderItem) {
        return orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public OrderItem queryOrderItemById(int id) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
        //为orderItem对象的product属性注入值
        setProduct(orderItem);

        return orderItem;
    }

    @Override
    public List<OrderItem> queryOrderItemByOid(int oid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(oid);
        example.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        //为orderItems集合中的所有orderItem对象的product属性注入值
        setProduct(orderItems);

        return orderItems;
    }

    @Override
    public List<OrderItem> listByUid(int uid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid).andOidIsNull();
        example.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        setProduct(orderItems);

        return orderItems;
    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample example = new OrderItemExample();
        example.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        //为orderItems集合中的所有orderItem对象的product属性注入值
        setProduct(orderItems);
        return orderItems;
    }

    @Override
    public int getSaleCount(int pid) {
        OrderItemExample example = new OrderItemExample();
        //注意这里查询出来的订单项的oid不能为null
        example.createCriteria().andPidEqualTo(pid).andOidIsNotNull();
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);

        int saleCount = 0;
        if (!orderItems.isEmpty()) {
            for (OrderItem orderItem : orderItems) {
                if ("yes".equals(orderItem.getPaymentStatus()))
                    saleCount += orderItem.getNumber();
            }
        }

        return saleCount;
    }

    @Override
    public void setProduct(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems)
            setProduct(orderItem);
    }

    @Override
    public void setProduct(OrderItem orderItem) {
        Product product = productService.queryProductById(orderItem.getPid());
        orderItem.setProduct(product);
    }
}
