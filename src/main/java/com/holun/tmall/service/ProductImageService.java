package com.holun.tmall.service;

import com.holun.tmall.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductImageService {
    //单个图片（一个产品对应多个单个图片）
    String type_single = "type_single";
    //详情图片（一个产品对应多个详情图片）
    String type_detail = "type_detail";

    //增加产品图片到ProductImage表
    int addProductImage(ProductImage productImage);

    //上传产品图片
    void uploadProductImage(ProductImage productImage, MultipartFile[] images, String... imagesLocation);

    //根据id,从ProductImage表中删除某个产品图片
    int deleteProductImageById(int id);

    //删除已上传的产品图片
    void deleteUploadProductImage(ProductImage productImage, String... imagesLocation);

    //根据id,查找某个产品图片
    ProductImage queryProductImageById(int id);

    //根据pid（产品id），图片类型（type）查找对应的产品图片（一个产品对应多个产品图片）
    List<ProductImage> list(int pid, String type);

    //对于产品图片，本项目不提供修改功能。如果要修改，删除后，重新上传新图片即可
}
