package com.holun.tmall.service;

import com.holun.tmall.entity.Property;

import java.util.List;

public interface PropertyService {

    //增加属性
    int addProperty(Property property);

    //根据id,删除某个属性
    int deletePropertyById(int id);

    //修改某个属性（根据id,修改某个属性）
    int updateProperty(Property property);

    //根据属性id,查找某个属性
    Property queryPropertyById(int id);

    //根据分类id，查找属性（一个分类对应多个属性）
    List<Property> list(int cid);

    //为查询出来的properties集合中的每个property对象的category属性注入值
    void setCategory(List<Property> properties);

    //为查询出来的property对象的category属性注入值
    void setCategory(Property property);
}
