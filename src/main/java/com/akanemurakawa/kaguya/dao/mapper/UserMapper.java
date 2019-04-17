package com.akanemurakawa.kaguya.dao.mapper;

import java.util.List;

import com.hanaeyuuma.freeblogs.model.User;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-6-6
 */
public interface UserMapper {

	User selectByPrimaryKey(Integer id);

	User selectByPrimaryKeyWithFriend(Integer id);

	User selectByEmailAndPassword(User user);

	User selectByPassword(User user);

	Integer selectIdByPassword(User user);

	int updatePasswordByPrimaryKey(User user);

	int updateInfoByPrimaryKeySelective(User record);

	int updateAvatar(User user);

	Integer selectExistUserByUserName(User user);

	Integer selectExistUserByUserEmail(User user);

	Integer updatePasswordByEmail(User user);

	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	int updateByPrimaryKey(User record);

	List<User> batchSelect(List<Integer> uid);

	int batchInsert(List<User> userList);

	List<User> selectAllUserInfo();
	
	int batchDelete(List<Integer> idList);
	
	List<User> selectUserInfo(User user);

}