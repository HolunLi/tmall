package com.holun.tmall.entity;

public class User {
    //用户id
    private Integer id;
    //用户名
    private String name;
    //密码
    private String pwd;
    //用户的权限
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    //getAnonymousName方法用于在显示评价者的时候进行匿名显示
    public String getAnonymousName(){
        if(null == name)
            return null;

        if(name.length() <= 1)
            return "*";

        if(name.length() == 2)
            return name.substring(0,1) + "*";

        char[] cs =name.toCharArray();
        for (int i = 1; i < cs.length-1; i++) {
            cs[i] = '*';
        }
        return new String(cs);
    }
}