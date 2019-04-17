package com.akanemurakawa.kaguya.model.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 友情链接信息
 */
@Data
public class Friend implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 友情链接名称
     */
    private String name;

    /**
     * 友情链接url
     */
    private String link;
}