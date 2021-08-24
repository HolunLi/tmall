package com.holun.tmall.service;

import com.holun.tmall.entity.Category;
import com.holun.tmall.entity.Product;

import java.util.List;

public interface ProductService {

    //增加产品
    int addProduct(Product product);

    //根据id,删除某个产品
    int deleteProductById(int id);

    //修改某个产品（根据id,修改某个产品）
    int updateProduct(Product product);

    //根据产品id,查找某个产品
    Product queryProductById(int id);

    //根据分类id，查找产品（一个分类对应多个产品）
    List<Product> queryProductByCid(int cid);

    //根据关键字，查询对应的产品（模糊查询）
    List<Product> search(String keyword);

    //为查询出来的products集合中的每个product对象的category属性注入值
    void setCategory(List<Product> products);

    //为查询出来的product对象的category属性注入值
    void setCategory(Product product);

    //为查询出来的products集合中的每个product对象的firstProductImage属性注入值。
    void setFirstProductImage(List<Product> products);

    //为查询出来的product对象的firstProductImage属性注入值。
    void setFirstProductImage(Product product);

    //为查询出来的products集合中的每个product对象的saleCount属性和reviewCount属性注入值
    void setSaleCountAndReviewCount(List<Product> products);

    //为查询出来的product对象的saleCount属性和reviewCount属性注入值
    void setSaleCountAndReviewCount(Product product);

    //为查询出来的categories集合中的每个category对象的products属性注入值
    void setProducts(List<Category> categories);

    //为查询出来的category对象的products属性注入值
    void setProducts(Category category);

    //为查询出来的categories集合中的每个category对象的productsByRow属性注入值
    void setProductsByRow(List<Category> categories);
}
