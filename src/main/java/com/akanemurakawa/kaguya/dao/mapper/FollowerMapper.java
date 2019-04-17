package com.akanemurakawa.kaguya.dao.mapper;

import java.util.List;
import com.akanemurakawa.kaguya.dao.base.BaseMapper;
import com.akanemurakawa.kaguya.model.entity.Admin;
import com.akanemurakawa.kaguya.model.entity.Follower;

public interface FollowerMapper extends BaseMapper<Admin> {

	List<Integer> selectFollowing(Integer uid);

	List<Integer> selectFollower(Integer uid);

	int insert(Follower record);

	int delete(Follower record);

	Integer isFollow(Follower record);
}
