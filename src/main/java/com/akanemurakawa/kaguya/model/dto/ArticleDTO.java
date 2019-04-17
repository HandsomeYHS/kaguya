package com.akanemurakawa.kaguya.model.dto;

import com.akanemurakawa.kaguya.model.entity.Category;
import com.akanemurakawa.kaguya.model.entity.Tag;
import com.akanemurakawa.kaguya.model.entity.User;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章信息DTO
 */
@Data
public class ArticleDTO implements Serializable {

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
     * 文章标题
     */
    private String title;

    /**
     * 发布时间
     */
    private Date createTime;

    /**
     * 文章摘要
     */
    private String sumary;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章点击数
     */
    private Integer hit;

    /**
     * 分类
     */
    private Category category;

    /**
     * 标签
     */
    private Tag tag;

    /**
     * 用户
     */
    private User user;
}