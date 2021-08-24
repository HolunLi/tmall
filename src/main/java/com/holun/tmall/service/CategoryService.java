package com.holun.tmall.service;

import com.holun.tmall.entity.Category;
import com.holun.tmall.entity.Product;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface CategoryService {

    //增加分类
    int addCategory(Category category);

    //上传分类图片
    void uploadCategoryImage(Category category, MultipartFile image, String path);

    //根据id,删除某个分类
    int deleteCategoryById(int id);

    //删除已上传分类图片
    void deleteUploadCategoryImage(int id, String path);

    //修改某个分类（根据id,修改某个分类）
    int updateCategory(Category category);

    //修改已上传分类图片
    void updateCategoryImage(Category category, MultipartFile image, String path);

    //根据id,查找某个分类
    Category queryCategoryById(int id);

    //查找所有的分类
    List<Category> list();

    //根据排序条件，对当前分类包含的所有产品进行排序
    void sort(List<Product> products, String sort);
}
