package com.holun.tmall.service.impl;

import com.holun.tmall.entity.Review;
import com.holun.tmall.entity.ReviewExample;
import com.holun.tmall.entity.User;
import com.holun.tmall.mapper.ReviewMapper;
import com.holun.tmall.service.ReviewService;
import com.holun.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewMapper reviewMapper;
    private UserService userService;

    @Autowired
    public void setReviewMapper(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public int addReview(Review review) {
        return reviewMapper.insertSelective(review);
    }

    @Override
    public int deleteReviewById(int id) {
        return reviewMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateReviewById(Review review) {
        return reviewMapper.updateByPrimaryKeySelective(review);
    }

    @Override
    public Review queryReviewById(int id) {
        Review review = reviewMapper.selectByPrimaryKey(id);
        //为review对象的user属性注入值
        setUser(review);

        return review;
    }

    @Override
    public List<Review> queryReviewByPid(int pid) {
        ReviewExample example = new ReviewExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");
        List<Review> reviews = reviewMapper.selectByExample(example);
        //为reviews集合中的每个review对象的user属性注入值
        setUser(reviews);

        return reviews;
    }

    @Override
    public int getReviewCount(int pid) {
        return queryReviewByPid(pid).size();
    }

    @Override
    public void setUser(List<Review> reviews) {
        for (Review review : reviews) {
            setUser(review);
        }
    }

    @Override
    public void setUser(Review review) {
        User user = userService.queryUserById(review.getUid());
        review.setUser(user);
    }
}
