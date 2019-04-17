package com.akanemurakawa.kaguya.model.dto;

import com.akanemurakawa.kaguya.model.entity.Admin;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class NoticeDTO implements Serializable {

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

    /**
     * 管理员
     */
    private Admin admin;

}