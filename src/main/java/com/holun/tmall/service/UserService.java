package com.holun.tmall.service;

import com.holun.tmall.entity.User;

import java.util.List;

public interface UserService {
    //增加用户
    int addUser(User user);

    //根据id，删除用户
    int deleteUser(int id);

    //修改用户
    int updateUser(User user);

    //根据用户id,查找某个用户
    User queryUserById(int id);

    //查找所有的用户
    List<User> list();

    //根据用户名，查找某个用户是否存在
    boolean isExist(String name);

    //根据用户名和密码，查找用户
    User queryUserByNameAndPwd(String name, String pwd);

    //根据用户名查找用户
    User queryUserByName(String name);
}
