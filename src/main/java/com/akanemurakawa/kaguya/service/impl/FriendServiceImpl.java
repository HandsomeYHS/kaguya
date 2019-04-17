package com.akanemurakawa.kaguya.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaeyuuma.freeblogs.dao.FriendMapper;
import com.hanaeyuuma.freeblogs.model.Friend;
import com.hanaeyuuma.freeblogs.service.FriendService;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-6-10
 */
@Service
public class FriendServiceImpl implements FriendService {

	@Autowired
	private FriendMapper friendMapper;

	/**
	 * Friend:修改友情链接
	 */
	@Override
	public Boolean modifyFriendInfo(Friend friend) {
		Integer result = friendMapper.updateByUserPrimaryKeySelective(friend);
		if (1 == result) {
			return true;
		}
		return false;
	}

	/**
	 * Friend:添加友情链接
	 */
	@Override
	public int insertSelective(Friend record) {
		friendMapper.insertSelective(record);
		return 0;
	}

	/**
	 * Friend:添加友情链接
	 */
	@Override
	public int insert(Friend record) {
		return friendMapper.insert(record);
	}

}
