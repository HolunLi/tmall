package com.holun.tmall.mapper;

import com.holun.tmall.entity.Category;
import com.holun.tmall.entity.CategoryExample;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    //动态插入语句，会对插入的字段进行非空判断
    int insertSelective(Category record);

    //动态查询语句(就是xml映射文件中的包含动态sql的查询语句)，需要借助example对象，动态的添加条件
    List<Category> selectByExample(CategoryExample example);

    Category selectByPrimaryKey(Integer id);

    //动态更新语句，会对更新的字段进行非空判断
    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}