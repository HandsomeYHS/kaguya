package com.akanemurakawa.kaguya.model.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 评论信息
 */
@Data
public class Comment implements Serializable{

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
     * 评论内容
     */
	private String content;

    /**
     * 评论时间
     */
	private Date createTime;
}
