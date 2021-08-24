package com.holun.tmall.comparator;

import com.holun.tmall.entity.Product;

import java.util.Comparator;

/**
 * 人气比较器
 * 把评价多的产品放前面（降序）
 */
public class ProductReviewComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        return o2.getReviewCount() - o1.getReviewCount();
    }
}
