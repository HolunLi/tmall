package com.holun.tmall.service;


import com.holun.tmall.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    //增加订单项
    int addOrderItem(OrderItem orderItem);

    //将产品加入到购物车（实际上就是生成一个订单项）
    void addProductToCart(int pid, int number, int uid);

    //根据id，删除某个订单项
    int deleteOrderItemById(int id);

    //修改订单项
    int updateOrderItem(OrderItem orderItem);

    //根据订单项id,查找某个订单项
    OrderItem queryOrderItemById(int id);

    //根据订单id，查找当前订单包含的所有订单项
    List<OrderItem> queryOrderItemByOid(int oid);

    //根据用户id，查询当前用户下订单的所有订单项
    List<OrderItem> listByUid(int uid);

    //查找所有的订单项
    List<OrderItem> list();

    //根据产品id，查询该产品的销量
    int getSaleCount(int pid);

    //为查询出来的orderItems集合中的所有orderItem对象的product属性注入值
    void setProduct(List<OrderItem> orderItems);

    //为查询出来的orderItem对象的product属性注入值
    void setProduct(OrderItem orderItem);
}
