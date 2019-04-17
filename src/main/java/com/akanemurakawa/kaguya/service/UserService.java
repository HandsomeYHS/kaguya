package com.akanemurakawa.kaguya.service;

import com.akanemurakawa.kaguya.model.entity.Comment;
import com.akanemurakawa.kaguya.model.entity.Follower;
import com.akanemurakawa.kaguya.model.entity.User;
import com.akanemurakawa.kaguya.model.entity.data.Msg;

import java.util.List;

public interface UserService {
	
	User selectByPrimaryKey(Integer id);
	
	User selectByPrimaryKeyWithFriend(Integer id);
	
	Integer selectExistUserByUserEmail(User user);
	
	User checkUserLogin(User user);
	
	User checkUserCookie(User user);
	
	Msg modifyUserPassword(Integer id, String oldPassword, String newPassword);
	
	Boolean modifyUserInfo(User user);
	
	int updateAvatar(User user);
	
	Integer updatePasswordByEmail(User user);

	int deleteAvatar(Integer id);
	
	int Follow(Integer uid, Integer followingId);
	
	List<Integer> getFollowerIds(Integer uid);
	
	List<Integer> getFollowingIds(Integer uid);
	
	List<User> getFollower(Integer uid);
	
	List<User> getFollowing(Integer uid);
	
	Integer isFollow(Follower record);
	
	Integer deleteFollow(Integer uid, Integer followingId);
	
	Integer checkExistUserName(String username);
	
	int insertSelective(User record);
	
	int addComment(Comment record);
	
	List<Comment> selectComment(Integer articleId);
	
	int deleteComment(Integer id);
	
	int batchInsert(List<User> userList);
	
	List<User> selectAllUserInfo();
	
	int deleteByPrimaryKey(Integer id);
	
	int deleteBatch(List<Integer> idList);
	
	List<User> selectUserInfo(User user);
}
