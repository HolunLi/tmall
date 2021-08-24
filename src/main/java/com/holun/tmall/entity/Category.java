package com.holun.tmall.entity;

import java.util.List;

public class Category {
    //分类id（主键，自增）
    private Integer id;
    //分类名
    private String name;
    //product是非数据库表字段，表示一个分类包含的所有产品
    private List<Product> products;
    //productsByRow是非数据库表字段，表示首页竖状分类菜单右侧的推荐产品列表
    private List<List<Product>> productsByRow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }
}