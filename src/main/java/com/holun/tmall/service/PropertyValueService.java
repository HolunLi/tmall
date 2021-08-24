package com.holun.tmall.service;

import com.holun.tmall.entity.Product;
import com.holun.tmall.entity.PropertyValue;

import java.util.List;

public interface PropertyValueService {

    //为什么要初始化PropertyValue？ 因为属性值的增加，完全依赖于属性的增加，所以需要通过初始化来进行自动地增加属性值，以便于后面的修改。
    void initPropertyValue(Product product);

    //增加属性值
    int addPropertyValue(PropertyValue propertyValue);

    //修改属性值（发送异步请求更改产品属性值，不刷新整个页面）
    int updatePropertyValue(PropertyValue propertyValue);

    //根据产品id，查找对应的所有属性值
    List<PropertyValue> list(int productId);

    //根据产品id和属性id，查找对应的属性值（产品id和属性id，能唯一确认一个属性值）
    PropertyValue queryPropertyValue(int productId, int propertyId);

    //不提供删除属性值的操着，属性值的删除取决于属性的删除

    //为查询出来的propertyValues集合中的每个propertyValue对象的property属性注入值。
    void setProperty(List<PropertyValue> propertyValues);

    //为查询出来的propertyValue对象的property属性注入值。
    void setProperty(PropertyValue propertyValue);
}
