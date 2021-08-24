package com.holun.tmall.entity;

import com.holun.tmall.service.OrderService;

import java.util.Date;
import java.util.List;

public class Order {
    //订单id（主键，自增）
    private Integer id;
    //订单号
    private String orderCode;
    //收货地址
    private String address;
    //邮编
    private String post;
    //收货人信息
    private String receiver;
    //手机号码
    private String mobile;
    //用户备注信息
    private String userMessage;
    //订单创建日期
    private Date createDate;
    //支付日期
    private Date payDate;
    //发货日期
    private Date deliveryDate;
    //确认收货日期
    private Date confirmDate;
    //uid是外键，参考user表的主键id（一个用户有多个订单，并且用户只有在提交订单之后，才会生成订单）
    private Integer uid;
    //订单状态（包含六种状态，分别是待付款、待发货、待收货、待评介，订单已完成，订单已删除）
    private String status;
    //user是非order表字段，表示下该订单的用户
    private User user;
    //orderItems是非order表字段，表示一个订单包含的所有订单项（一个订单可以包含多个订单项，一个订单项就是一种类型的商品）
    private List<OrderItem> orderItems;
    //total是非order表字段，表示该订单的总计金额
    private float total;
    //totalNumber是非order表字段，表示该订单总共包含多少个商品
    private int totalNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage == null ? null : userMessage.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public String getStatusDesc(){
        String desc ="未知";

        switch(status){
            case OrderService.waitPay: desc="待付款"; break;
            case OrderService.waitDelivery: desc="待发货"; break;
            case OrderService.waitConfirm: desc="待收货"; break;
            case OrderService.waitReview: desc="待评价"; break;
            case OrderService.finish: desc="订单已完成"; break;
            case OrderService.delete: desc="订单已删除"; break;
            default: desc="未知";
        }

        return desc;
    }
}