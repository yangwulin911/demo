package com.ywl.entity;

import org.codehaus.jackson.annotate.JsonBackReference;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author yangwulin
 * @since 2020-05-15 17:59:24
 */
public class User implements Serializable {
    private static final long serialVersionUID = 539871554184952644L;
    
    private Integer id;
    
    private String account;
    
    private String description;
    
    private String password;
    
    private String name;

    /**
     * 用户 --角色 多对一
     */
//    @JsonBackReference
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinTable(name = "um_t_role_user", joinColumns = {@JoinColumn(name = "userId")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
//    private Role role;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}