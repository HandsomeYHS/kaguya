package com.akanemurakawa.kaguya.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private Integer id;

    private String userCode;

    private String username;

    private String status;

    private String email;

    private String password;

    private String bio;

    private String gravatar;

    private String github;

    private String company;

    private String location;

    private String organizations;

    private Date regitsterTime;

    private static final long serialVersionUID = 1L;
}