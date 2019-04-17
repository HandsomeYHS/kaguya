package com.akanemurakawa.kaguya.model.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 用户
 */
@Data
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

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
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 个人简介
     */
    private String description;

    /**
     * GitHub的url
     */
    private String github;

    /**
     * twitter的url
     */
    private String twitter;

    /**
     * weibo的url
     */
    private String weibo;
}