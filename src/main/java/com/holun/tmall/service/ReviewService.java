package com.holun.tmall.service;

import com.holun.tmall.entity.Review;

import java.util.List;

public interface ReviewService {

    //增加一条评价
    int addReview(Review review);

    //根据评介id，删除某条评价
    int deleteReviewById(int id);

    //根据评介id，修改某条评价
    int updateReviewById(Review review);

    //根据评介id，查找某条评价
    Review queryReviewById(int id);

    //根据产品id，查找该产品包含的所有评价
    List<Review> queryReviewByPid(int pid);

    //根据产品id，查找该产品包含的所有评价的总数
    int getReviewCount(int pid);

    //为查询出来的reviews集合中的每个review对象的user属性注入值
    void setUser(List<Review> reviews);

    //为查询出来的review对象的user属性注入值
    void setUser(Review review);
}
