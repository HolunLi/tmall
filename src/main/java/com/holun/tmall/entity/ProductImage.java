package com.holun.tmall.entity;

public class ProductImage {
    //产品图片id（主键，自增）
    private Integer id;
    //pid是外键，参考product表的主键id（一个产品对应多个产品图片）
    private Integer pid;
    //产品图片的类型（产品图片分为两种类型，一种是单个图片，一种是详情图片）
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}