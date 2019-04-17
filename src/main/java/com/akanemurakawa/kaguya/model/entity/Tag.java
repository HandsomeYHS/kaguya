package com.akanemurakawa.kaguya.model.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 标签
 */
@Data
public class Tag implements Serializable {

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
     * 标签名称
     */
    private String name;
}