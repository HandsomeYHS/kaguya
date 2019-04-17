package com.akanemurakawa.kaguya.model.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 文章信息
 */
@Data
public class Article implements Serializable {

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

    public Article(Integer id, String title, String sumary, String content) {
        super();
        this.id = id;
        this.title = title;
        this.sumary = sumary;
        this.content = content;
    }

    public Article(Integer userId, String title, Date createTime, String sumary, Integer hit, String content) {
        super();
        this.userId = userId;
        this.title = title;
        this.createTime = createTime;
        this.sumary = sumary;
        this.hit = hit;
        this.content = content;
    }

    public Article(Integer id, Integer userId, String title, Date createTime, String sumary, Integer hit, String content) {
        super();
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.createTime = createTime;
        this.sumary = sumary;
        this.hit = hit;
        this.content = content;
    }
}