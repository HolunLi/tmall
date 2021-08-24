package com.holun.tmall.service.impl;

import com.holun.tmall.entity.Category;
import com.holun.tmall.entity.Property;
import com.holun.tmall.entity.PropertyExample;
import com.holun.tmall.mapper.PropertyMapper;
import com.holun.tmall.service.CategoryService;
import com.holun.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {
    private PropertyMapper propertyMapper;
    private CategoryService categoryService;

    @Autowired
    public void setPropertyMapper(PropertyMapper propertyMapper) {
        this.propertyMapper = propertyMapper;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public int addProperty(Property property) {
        return propertyMapper.insertSelective(property);
    }

    @Override
    public int deletePropertyById(int id) {
        return propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateProperty(Property property) {
        return propertyMapper.updateByPrimaryKeySelective(property);
    }

    @Override
    public Property queryPropertyById(int id) {
        //为查询出来的property对象的category属性注入值
        Property property = propertyMapper.selectByPrimaryKey(id);
        setCategory(property);

        return property;
    }

    @Override
    public List<Property> list(int cid) {
        //example对象用于添加查询的条件
        PropertyExample example = new PropertyExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List<Property> properties = propertyMapper.selectByExample(example);
        //为properties集合中的每个property对象的category属性注入值
        setCategory(properties);

        return properties;
    }

    @Override
    public void setCategory(List<Property> properties) {
        for (Property property : properties) {
            setCategory(property);
        }
    }

    @Override
    public void setCategory(Property property) {
        Category category = categoryService.queryCategoryById(property.getCid());
        property.setCategory(category);
    }

}
