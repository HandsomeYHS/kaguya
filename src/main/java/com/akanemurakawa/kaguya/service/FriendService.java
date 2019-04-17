package com.akanemurakawa.kaguya.service;

import com.hanaeyuuma.freeblogs.model.Friend;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-6-10
 */
public interface FriendService {
	
	Boolean modifyFriendInfo(Friend friend);
	
	int insertSelective(Friend record);
	
	int insert(Friend record);
}
