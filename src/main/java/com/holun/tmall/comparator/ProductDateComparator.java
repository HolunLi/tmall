package com.holun.tmall.comparator;

import com.holun.tmall.entity.Product;

import java.util.Comparator;

/**
 * 新品比较器
 * 将把创建日期晚的产品放前面
 */
public class ProductDateComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {
        //Date类中的compareTo方法用于比较两个Date对象的大小
        return o2.getCreateDate().compareTo(o1.getCreateDate());
    }
}
