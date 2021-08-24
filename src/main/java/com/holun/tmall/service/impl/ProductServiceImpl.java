package com.holun.tmall.service.impl;

import com.holun.tmall.entity.Category;
import com.holun.tmall.entity.Product;
import com.holun.tmall.entity.ProductExample;
import com.holun.tmall.entity.ProductImage;
import com.holun.tmall.mapper.ProductMapper;
import com.holun.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductMapper productMapper;
    private CategoryService categoryService;
    private ProductImageService productImageService;
    private OrderItemService orderItemService;
    private ReviewService reviewService;

    @Autowired
    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setProductImageService(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @Autowired
    public void setOrderItemService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public int addProduct(Product product) {
        return productMapper.insertSelective(product);
    }

    @Override
    public int deleteProductById(int id) {
        return productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateProduct(Product product) {
        return productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public Product queryProductById(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        //为product对象的category属性注入值。
        setCategory(product);
        //为product对象的firstProductImage属性注入值。
        setFirstProductImage(product);
        //为product对象的saleCount和reviewCount属性注入值.
        setSaleCountAndReviewCount(product);

        return product;
    }

    @Override
    public List<Product> queryProductByCid(int cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        List<Product> products = productMapper.selectByExample(example);
        //为products集合中的每个product对象的firstProductImage属性注入值。
        setFirstProductImage(products);
        setSaleCountAndReviewCount(products);

        return products;
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.createCriteria().andNameLike('%' + keyword + '%');
        example.setOrderByClause("id desc");
        List<Product> products = productMapper.selectByExample(example);
        setFirstProductImage(products);
        setSaleCountAndReviewCount(products);

        return products;
    }

    @Override
    public void setCategory(List<Product> products) {
        for (Product product : products) {
            setCategory(product);
        }
    }

    @Override
    public void setCategory(Product product) {
        Category category = categoryService.queryCategoryById(product.getCid());
        product.setCategory(category);
    }

    @Override
    public void setFirstProductImage(List<Product> products) {
        for (Product product : products) {
            setFirstProductImage(product);
        }
    }

    @Override
    public void setFirstProductImage(Product product) {
        List<ProductImage> productImages = productImageService.list(product.getId(), ProductImageService.type_single);
        if (!productImages.isEmpty())
            product.setFirstProductImage(productImages.get(0));
    }

    @Override
    public void setSaleCountAndReviewCount(List<Product> products) {
        for (Product product : products) {
            setSaleCountAndReviewCount(product);
        }
    }

    @Override
    public void setSaleCountAndReviewCount(Product product) {
        int saleCount = orderItemService.getSaleCount(product.getId());
        int reviewCount = reviewService.getReviewCount(product.getId());
        product.setSaleCount(saleCount);
        product.setReviewCount(reviewCount);
    }

    @Override
    public void setProducts(List<Category> categories) {
        for (Category category : categories)
            setProducts(category);
    }

    @Override
    public void setProducts(Category category) {
        List<Product> products = queryProductByCid(category.getId());
        category.setProducts(products);
    }

    @Override
    public void setProductsByRow(List<Category> categories) {
        //productNumberEachRow是首页竖状分类菜单右侧的推荐产品区域，每行显示的产品数量
        int productNumberEachRow = 8;
        for (Category category : categories) {
            //查询当前分类包含多少个产品
            List<Product> products = queryProductByCid(category.getId());
            List<List<Product>> productsByRow =  new ArrayList<>();
            for (int i = 0; i < products.size(); i += productNumberEachRow) {
                int size = i + productNumberEachRow;
                size = size > products.size() ? products.size() : size;
                //将当前分类包含的所有产品，每8个截取一次，组成一个新的列表，作为推荐产品区域一行显示的产品。
                List<Product> productsOfEachRow = products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }
}
