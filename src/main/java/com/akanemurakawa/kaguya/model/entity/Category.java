package com.akanemurakawa.kaguya.model.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 分类信息
 */
@Data
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 文章id
     */
    private Integer articleId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 分类名称
     */
    private String name;
}