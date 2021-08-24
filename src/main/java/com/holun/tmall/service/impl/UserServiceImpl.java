package com.holun.tmall.service.impl;

import com.holun.tmall.entity.User;
import com.holun.tmall.entity.UserExample;
import com.holun.tmall.mapper.UserMapper;
import com.holun.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int addUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int deleteUser(int id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User queryUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> list() {
        UserExample example = new UserExample();
        example.setOrderByClause("id desc");
        return userMapper.selectByExample(example);
    }

    @Override
    public boolean isExist(String name) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(example);
        if (users.isEmpty())
            return false;

        return true;
    }

    @Override
    public User queryUserByNameAndPwd(String name, String pwd) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name).andPwdEqualTo(pwd);
        List<User> users = userMapper.selectByExample(example);
        if (users.isEmpty())
            return null;

        return users.get(0);
    }

    @Override
    public User queryUserByName(String name) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(example);
        if (users.isEmpty())
            return null;

        return users.get(0);
    }
}
