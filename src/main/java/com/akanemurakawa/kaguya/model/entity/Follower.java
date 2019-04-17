package com.akanemurakawa.kaguya.model.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * 用户关注信息
 */
@Data
public class Follower implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private Integer id;

    /**
     * 关注别人的用户id，对应user表的id
     */
	private Integer userId;

    /**
     * 被关注的人id，对应user表的id
     */
	private Integer followingId;

}
