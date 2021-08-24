package com.holun.tmall.entity;

import java.util.Date;

public class Review {
    //评价id
    private Integer id;
    //评价的内容
    private String content;
    //uid是外键，参考user表的主键id（一个用户会包含多条评介）
    private Integer uid;
    //pid是外键，参考product表的主键id（一个产品会有多条评介）
    private Integer pid;
    //评价创建的时间
    private Date createDate;
    //user是非review表字段,表示该评价对应的用户
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}