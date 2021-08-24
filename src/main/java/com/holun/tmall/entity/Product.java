package com.holun.tmall.entity;

import java.util.Date;
import java.util.List;

public class Product {
    //产品id（主键，自增）
    private Integer id;
    //产品名
    private String name;
    //小标题
    private String subTitle;
    //原价
    private Float originalPrice;
    //优惠价
    private Float promotePrice;
    //产品库存
    private Integer stock;
    //cid是外键，参考category表的主键id
    private Integer cid;
    //产品的创建日期
    private Date createDate;
    //category是非product表字段，表示该产品属于哪个分类（一个分类包含多个产品）
    private Category category;
    //firstProductImage是非product表字段，表示该产品的第一个单个类型的产品图片
    private ProductImage firstProductImage;
    //productSingleImages是非product表字段，表示该产品包含的所有单个类型的产品图片
    private List<ProductImage> productSingleImages;
    //productDetailImages是非product表字段，表示该产品包含的所有详情类型的产品图片
    private List<ProductImage> productDetailImages;
    //saleCount是非product表字段，表示产品的销量
    private int saleCount;
    //reviewCount是非product表字段，表示产品包含评价数量
    private int reviewCount;

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

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? null : subTitle.trim();
    }

    public Float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Float getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(Float promotePrice) {
        this.promotePrice = promotePrice;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductImage getFirstProductImage() {
        return firstProductImage;
    }

    public void setFirstProductImage(ProductImage firstProductImage) {
        this.firstProductImage = firstProductImage;
    }

    public List<ProductImage> getProductSingleImages() {
        return productSingleImages;
    }

    public void setProductSingleImages(List<ProductImage> productSingleImages) {
        this.productSingleImages = productSingleImages;
    }

    public List<ProductImage> getProductDetailImages() {
        return productDetailImages;
    }

    public void setProductDetailImages(List<ProductImage> productDetailImages) {
        this.productDetailImages = productDetailImages;
    }

    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }
}