package com.holun.tmall.comparator;

import com.holun.tmall.entity.Product;

import java.util.Comparator;

/**
 * 价格比较器
 * 把价格低的产品放前面（升序）
 */
public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        //使用包装类Float的静态方法compare()比较float数据
        return  Float.compare(o1.getPromotePrice(), o2.getPromotePrice());
    }
}
