package com.holun.tmall.entity;

public class PropertyValue {
    //属性值id（主键，自增）
    private Integer id;
    //productId是外键，参考product表的主键id（一个产品有多个属性，对应的就有多个属性值）
    private Integer productId;
    //propertyId是外键，参考property表的主键id（一个属性有多个属性值）
    //一个产品id和一个属性id，可以唯一对应一个属性
    private Integer propertyId;
    //属性值
    private String value;
    //property是非propertyvalue表中的字段，表示该属性值对应的属性
    private Property property;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Integer propertyId) {
        this.propertyId = propertyId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}