package com.holun.tmall.service.impl;

import com.holun.tmall.entity.Product;
import com.holun.tmall.entity.Property;
import com.holun.tmall.entity.PropertyValue;
import com.holun.tmall.entity.PropertyValueExample;
import com.holun.tmall.mapper.PropertyValueMapper;
import com.holun.tmall.service.PropertyService;
import com.holun.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    private PropertyValueMapper propertyValueMapper;
    private PropertyService propertyService;

    @Autowired
    public void setPropertyValueMapper(PropertyValueMapper propertyValueMapper) {
        this.propertyValueMapper = propertyValueMapper;
    }

    @Autowired
    public void setPropertyService(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Override
    public void initPropertyValue(Product product) {
        //根据分类id查找，该分类有多少个属性
        List<Property> properties = propertyService.list(product.getCid());
        for (Property property : properties) {
            //如果根据产品id和属性id，查找对应的属性值
            PropertyValue propertyValue = queryPropertyValue(product.getId(), property.getId());
            //如果根据产品id和属性id，查找不到对应的属性值，就新增这个属性值
            if (propertyValue == null) {
                propertyValue = new PropertyValue();
                propertyValue.setProductId(product.getId());
                propertyValue.setPropertyId(property.getId());
                propertyValue.setValue("");
                addPropertyValue(propertyValue);
            }
        }
    }

    @Override
    public int addPropertyValue(PropertyValue propertyValue) {
        return propertyValueMapper.insertSelective(propertyValue);
    }

    @Override
    public int updatePropertyValue(PropertyValue propertyValue) {
        return propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }

    @Override
    public List<PropertyValue> list(int productId) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andProductIdEqualTo(productId);
        //根据产品id，查找该产品的所有属性的属性值
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(example);
        //为propertyValues集合中的每个propertyValue对象的property属性注入值。
        setProperty(propertyValues);

        return propertyValues;
    }

    @Override
    public PropertyValue queryPropertyValue(int productId, int propertyId) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andProductIdEqualTo(productId).andPropertyIdEqualTo(propertyId);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(example);
        if (propertyValues.isEmpty())
            return null;

        return propertyValues.get(0);
    }

    @Override
    public void setProperty(List<PropertyValue> propertyValues) {
        for (PropertyValue propertyValue : propertyValues)
            setProperty(propertyValue);
    }

    @Override
    public void setProperty(PropertyValue propertyValue) {
        Property property = propertyService.queryPropertyById(propertyValue.getPropertyId());
        propertyValue.setProperty(property);
    }

}
