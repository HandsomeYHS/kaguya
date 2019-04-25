package com.akanemurakawa.kaguya.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Repository implements Serializable {
    private Integer id;

    private String userCode;

    private String title;

    private Date createTime;

    private String description;

    private String content;

    private String authorityType;

    private static final long serialVersionUID = 1L;
}