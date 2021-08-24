package com.holun.tmall.comparator;

import com.holun.tmall.entity.Product;

import java.util.Comparator;

/**
 * 综合比较器
 * 把 销量x评价 高的产品放前面（降序）
 */
public class ProductAllComparator implements Comparator<Product> {
    /**
     * 在compare方法中定义比较规则
     * 当compare方法的返回值大于0时，交换o1和o2的位置；小于等于0时，o1和o2的位置不发生改变。
     */
    @Override
    public int compare(Product o1, Product o2) {
        return o2.getSaleCount()*o2.getReviewCount() - o1.getSaleCount()*o1.getReviewCount();
    }
}
