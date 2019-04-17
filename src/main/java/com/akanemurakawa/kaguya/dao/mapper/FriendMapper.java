package com.akanemurakawa.kaguya.dao.mapper;

import com.akanemurakawa.kaguya.dao.base.BaseMapper;
import com.akanemurakawa.kaguya.model.entity.Friend;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-6-6
 */
public interface FriendMapper extends BaseMapper<Friend> {

	int deleteByPrimaryKey(Integer id);

	int insert(Friend record);

	int insertSelective(Friend record);

	Friend selectByPrimaryKey(Integer id);

	int updateByUserPrimaryKeySelective(Friend record);

	int updateByPrimaryKey(Friend record);
}