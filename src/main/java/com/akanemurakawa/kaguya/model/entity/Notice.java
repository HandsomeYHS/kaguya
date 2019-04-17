package com.akanemurakawa.kaguya.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告信息
 */
@Data
public class Notice	implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 管理员id
     */
    private Integer adminId;

    /**
     * 公共标题
     */
    private String title;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 公共摘要
     */
    private String sumary;

    /**
     * 公共内容
     */
    private String content;

    public Notice(Integer adminId, String title,  String sumary, String content) {
        super();
        this.adminId = adminId;
        this.title = title;
        this.createTime = createTime;
        this.sumary = sumary;
        this.content = content;
    }

    public Notice(Integer adminId, String title, Date createTime, String sumary, String content) {
        super();
        this.adminId = adminId;
        this.title = title;
        this.createTime = createTime;
        this.sumary = sumary;
        this.content = content;
    }

    public Notice(Integer id, Integer adminId, String title, Date createTime, String sumary, String content) {
        super();
        this.id = id;
        this.adminId = adminId;
        this.title = title;
        this.createTime = createTime;
        this.sumary = sumary;
        this.content = content;
    }
}