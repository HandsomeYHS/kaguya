package com.akanemurakawa.kaguya.model.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 管理员信息
 */
@Data
public class Admin implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Integer id;

    /**
     * 管理员登录账号
     */
	private String acount;

    /**
     * 管理员用户名
     */
    private String name;

    /**
     * 管理员密码
     */
    private String password;
}