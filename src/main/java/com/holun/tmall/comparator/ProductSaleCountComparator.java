package com.holun.tmall.comparator;


import com.holun.tmall.entity.Product;

import java.util.Comparator;

/**
 * 销量比较器
 * 把销量高的产品放前面（降序）
 */
public class ProductSaleCountComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        return o2.getSaleCount() - o1.getSaleCount();
    }
}
