package com.holun.tmall.entity;

public class Property {
    //属性id（主键，自增）
    private Integer id;
    //cid是外键，参考category表的主键id
    private Integer cid;
    //属性名（在本项目中，相同分类下的产品的包含的属性都是相同的，但各自的属性值不同）
    private String name;
    //category是非Property表中的字段，表示该属性对应的分类
    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}