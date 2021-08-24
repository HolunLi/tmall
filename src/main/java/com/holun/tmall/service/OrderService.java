package com.holun.tmall.service;

import com.holun.tmall.entity.Order;
import com.holun.tmall.entity.OrderItem;
import com.holun.tmall.entity.Review;

import java.util.List;

public interface OrderService {
    //待付款
    String waitPay = "waitPay";
    //待发货
    String waitDelivery = "waitDelivery";
    //待收货
    String waitConfirm = "waitConfirm";
    //待评介
    String waitReview = "waitReview";
    //订单已完成
    String finish = "finish";
    //订单已删除
    String delete = "delete";

    //增加订单 (订单增加功能交由前台完成，后台不提供)
    int addOrder(Order order);

    //新增订单并更新订单项
    float addOrderAndUpdateOrderItem(Order order, List<OrderItem> orderItems);

    //新增订单和订单项
    float addOrderAndOrderItem(Order order, OrderItem orderItem);

    //根据id，删除某个订单 (订单删除功能交由前台完成，后台不提供)
    int deleteOrderById(int id);

    //修改订单
    int updateOrder(Order order);

    //更新订单、订单项并修改产品库存
    int updateOrderAndOrderItemAndProductStock(Order order);

    //更新订单和订单项，并新增评价
    int updateOrderAndOrderItemAndAddReview(Order order, OrderItem orderItem, Review review);

    //根据id,查找某个订单
    Order queryOrderById(int id);

    //查找所有的订单（所有用户下的订单）
    List<Order> list();

    //查询当前用户下的所有订单中，订单状态不是已删除的订单
    List<Order> list(int uid, String excludedStatus);

    //为orders集合中的所有order对象的user属性、orderItems属性、total属性、totalNumber属性注入值
    void fill(List<Order> orders);

    //为order对象的user属性、orderItems属性、total属性、totalNumber属性注入值
    void fill(Order order);
}
