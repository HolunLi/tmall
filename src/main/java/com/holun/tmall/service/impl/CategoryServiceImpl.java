package com.holun.tmall.service.impl;

import com.holun.tmall.comparator.*;
import com.holun.tmall.entity.Category;
import com.holun.tmall.entity.CategoryExample;
import com.holun.tmall.entity.Product;
import com.holun.tmall.mapper.CategoryMapper;
import com.holun.tmall.service.CategoryService;
import com.holun.tmall.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * 注意：组件之间不能存在循环依赖
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryMapper categoryMapper;

    @Autowired
    public void setCategoryMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public int addCategory(Category category) {
        return categoryMapper.insertSelective(category);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    @Override
    public void uploadCategoryImage(Category category, MultipartFile image, String path) {
        addCategory(category);

        //c.getId()+".jpg" 是上传到img/category路径下的图片的名字
        File file = new File(path,category.getId()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        //将图片上传到指定的路径下
        ImageUtil.uploadImageToDestination(image, file);
    }

    @Override
    public int deleteCategoryById(int id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    @Override
    public void deleteUploadCategoryImage(int id, String path) {
        deleteCategoryById(id);
        ImageUtil.deleteUploadImage(path + "/" + id + ".jpg");
    }

    @Override
    public int updateCategory(Category category) {
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    @Override
    public void updateCategoryImage(Category category, MultipartFile image, String path) {
        updateCategory(category);

        //如果更新分类时，重新上传了分类图片，就将新上传的图片覆盖原图片
        if(!image.isEmpty()){
            File file = new File(path,category.getId()+".jpg");
            if(!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            //将图片上传到指定的路径下
            ImageUtil.uploadImageToDestination(image, file);
        }
    }

    @Override
    public Category queryCategoryById(int id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        //setProducts(category);

        return category;
    }

    @Override
    public List<Category> list() {
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("id desc");

        return categoryMapper.selectByExample(example);
    }

    @Override
    public void sort(List<Product> products, String sort) {

        switch (sort) {
            case "review":
                //Collections工具类中的sort方法可以为List集合中的元素排序，前提是需要提供一个比较器
                Collections.sort(products, new ProductReviewComparator());
                break;
            case "date" :
                Collections.sort(products, new ProductDateComparator());
                break;

            case "saleCount" :
                Collections.sort(products, new ProductSaleCountComparator());
                break;

            case "price":
                Collections.sort(products, new ProductPriceComparator());
                break;

            case "all":
                Collections.sort(products, new ProductAllComparator());
                break;
        }

    }
}
