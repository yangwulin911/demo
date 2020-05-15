package com.ywl.entity;

import java.io.Serializable;

/**
 * 用户表(User)实体类
 *
 * @author yangwulin
 * @since 2020-05-14 09:31:36
 */
public class User implements Serializable {
    private static final long serialVersionUID = -61938857703570873L;
    /**
    * 主键
    */
    private Integer id;
    /**
    * 用户名
    */
    private String username;
    /**
    * 密码
    */
    private String password;
    /**
    * 盐
    */
    private String salt;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}