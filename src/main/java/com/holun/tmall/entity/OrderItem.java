package com.holun.tmall.entity;

public class OrderItem {
    //订单项id（主键，自增）
    private Integer id;
    //pid是外键，参考product表的主键id
    private Integer pid;
    //oid是外键，参考order表的主键id（一个订单可以包含多个种类的商品，一种商品就是一个订单项）
    private Integer oid;
    //uid是外键，参考user表的主键id
    private Integer uid;
    //购买的数量
    private Integer number;
    //该订单项对应的产品的评价状态（该字段在数据库表中默认为no，即未评价）
    private String reviewStatus;
    //该订单项对应的产品的付款状态（该字段在数据库表中默认为no，即未付款）
    private String paymentStatus;
    //product是非OrderItem表中的字段，表示该订单项对应的商品
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus == null ? null : reviewStatus.trim();
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}