package com.holun.tmall.mapper;

import com.holun.tmall.entity.PropertyValue;
import com.holun.tmall.entity.PropertyValueExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PropertyValueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValue record);

    int insertSelective(PropertyValue record);

    List<PropertyValue> selectByExample(PropertyValueExample example);

    PropertyValue selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PropertyValue record);

    int updateByPrimaryKey(PropertyValue record);
}