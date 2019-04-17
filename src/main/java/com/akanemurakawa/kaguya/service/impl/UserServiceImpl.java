package com.akanemurakawa.kaguya.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaeyuuma.freeblogs.dao.CommentMapper;
import com.hanaeyuuma.freeblogs.dao.FollowerMapper;
import com.hanaeyuuma.freeblogs.dao.UserMapper;
import com.hanaeyuuma.freeblogs.model.Comment;
import com.hanaeyuuma.freeblogs.model.Follower;
import com.hanaeyuuma.freeblogs.model.Msg;
import com.hanaeyuuma.freeblogs.model.User;
import com.hanaeyuuma.freeblogs.service.UserService;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-6-10
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private FollowerMapper followerMapper;

	@Autowired
	private CommentMapper commentMapper;

	/**
	 * User:通过用户的id来查询用户信息
	 */
	@Override
	public User selectByPrimaryKey(Integer id) {
		return userMapper.selectByPrimaryKey(id);
	}

	/**
	 * User:通过用户的id来查询用户信息，并带有友情链接信息
	 */
	@Override
	public User selectByPrimaryKeyWithFriend(Integer id) {
		return userMapper.selectByPrimaryKeyWithFriend(id);
	}

	/**
	 * User:验证用户的登录是否合法，如果存在该用户则返回该用户信息，否则返回null
	 */
	@Override
	public User checkUserLogin(User user) {
		User checkUser = userMapper.selectByEmailAndPassword(user);
		if (checkUser != null) {
			return checkUser;
		} else {
			return null;
		}
	}

	/**
	 * User:这一部分是用于查看用户是否使用记住了密码，如果有则查询相应的cookie的值，然后传到数据库验证密码 Name:"cookieSuzuki",
	 * Value:password
	 */
	@Override
	public User checkUserCookie(User user) {
		User checkUser = userMapper.selectByPassword(user);
		if (checkUser != null) {
			return checkUser;
		} else {
			return null;
		}
	}

	/**
	 * User:修改密码
	 */
	@Override
	public Msg modifyUserPassword(Integer id, String oldPassword, String newPassword) {
		/*
		 * 1.首先通过原来的密码获得id 2.如果存在id，并且该id和当前用户的id一致。这个时候说明用户的密码是正确的 3.通过id去修改密码
		 */
		User user = new User();
		user.setId(id);
		user.setUserPassword(oldPassword);
		Integer findId = userMapper.selectIdByPassword(user);
		// 如果通过密码找到了一个id，并且该id和当前用户的id一致，则进行修改密码
		if (id == findId) {
			user.setId(id);
			user.setUserPassword(newPassword);
			userMapper.updatePasswordByPrimaryKey(user);
		} else if (null == findId) {
			return Msg.fail("原密码输入错误！");
		}
		return Msg.success("密码修改成功，点击重新登录！");
	}

	/**
	 * User:修改资料，成功返回true， 失败返回false
	 */
	@Override
	public Boolean modifyUserInfo(User user) {
		User newUser = new User();
		newUser.setUserUsername(user.getUserUsername());
		/*
		 * 1.查询用户名是否已经存在，存在:newId不为null， 不存在: newId为null
		 * 2.注：这里不需要再判断输入的名字是否为""，因为即使是""查询的结果也是不存在该用户，即可以修改。
		 */
		Integer newId = userMapper.selectExistUserByUserName(newUser);
		// 如果不存在则进行插入数据
		if (null == newId) {
			userMapper.updateInfoByPrimaryKeySelective(user);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 修改头像，上传到数据库中
	 */
	@Override
	public int updateAvatar(User user) {
		return userMapper.updateAvatar(user);
	}

	/**
	 * 删除之前的头像
	 */
	@Override
	public int deleteAvatar(Integer id) {
		String oldAvatar = userMapper.selectByPrimaryKey(id).getUserAvatar();
		// 处理oldAvatar格式
		// http://localhost:8080/Suzuki/upload/img/avatar/2018/7/1_20180726161421723.jpg
		String[] avatar = oldAvatar.split(":");
		oldAvatar = avatar[avatar.length - 1];
		// 如果有端口，过滤掉端口
		oldAvatar = oldAvatar.replaceFirst("\\d*", "");
		// 过滤掉项目名
		oldAvatar = oldAvatar.replaceFirst("/(\\w*)", "");

		// 结果：/upload/img/avatar/2018/7/1_20180726162503862.jpg

		File file = new File(oldAvatar);
		// 如果存在则删除
		if (file.exists()) {
			file.delete();
		}
		return 0;
	}

	/**
	 * User:验证用户邮箱是否存在
	 */
	@Override
	public Integer selectExistUserByUserEmail(User user) {
		return userMapper.selectExistUserByUserEmail(user);
	}

	/**
	 * User:用户找回密码，通过邮箱进行修改。邮箱会进行验证
	 */
	@Override
	public Integer updatePasswordByEmail(User user) {
		return userMapper.updatePasswordByEmail(user);
	}

	/**
	 * User:关注别人
	 */
	@Override
	public int Follow(Integer uid, Integer followingId) {
		Follower follower = new Follower(uid, followingId);
		followerMapper.insert(follower);
		return 0;
	}

	/**
	 * User:用户查看所有粉丝的id
	 */
	@Override
	public List<Integer> getFollowerIds(Integer uid) {
		return followerMapper.selectFollower(uid);
	}

	/**
	 * User:用户查看所有关注的人的id
	 */
	@Override
	public List<Integer> getFollowingIds(Integer uid) {
		return followerMapper.selectFollowing(uid);
	}

	/**
	 * User:用户查看所有粉丝
	 */
	@Override
	public List<User> getFollower(Integer uid) {
		List<Integer> uids = followerMapper.selectFollower(uid);
		if (0 == uids.size()) {
			return null;
		}
		return userMapper.batchSelect(uids);
	}

	/**
	 * User:用户查看所有关注的人
	 */
	@Override
	public List<User> getFollowing(Integer uid) {
		List<Integer> uids = followerMapper.selectFollowing(uid);
		if (0 == uids.size()) {
			return null;
		}
		return userMapper.batchSelect(uids);
	}

	/**
	 * User:是否是粉丝或者是否关注
	 */
	@Override
	public Integer isFollow(Follower record) {
		return followerMapper.isFollow(record);
	}

	/**
	 * User:移除粉丝或取消关注
	 */
	@Override
	public Integer deleteFollow(Integer uid, Integer followingId) {
		Follower record = new Follower(uid, followingId);
		return followerMapper.delete(record);
	}

	/**
	 * User:检查用户名是否存储
	 */
	@Override
	public Integer checkExistUserName(String username) {
		User user = new User();
		user.setUserUsername(username);
		return userMapper.selectExistUserByUserName(user);
	}

	/**
	 * User:添加用户
	 */
	@Override
	public int insertSelective(User record) {
		return userMapper.insertSelective(record);
	}

	/**
	 * User:添加评论
	 */
	@Override
	public int addComment(Comment record) {
		return commentMapper.insert(record);
	}

	/**
	 * User:查看评论
	 */
	@Override
	public List<Comment> selectComment(Integer articleId) {
		return commentMapper.selectComment(articleId);
	}

	/**
	 * User:删除评论
	 */
	@Override
	public int deleteComment(Integer id) {
		return commentMapper.delete(id);
	}

	/**
	 * User:批量插入用户
	 */
	@Override
	public int batchInsert(List<User> userList) {
		return userMapper.batchInsert(userList);
	}

	/**
	 * User:查找所有用户信息
	 */
	@Override
	public List<User> selectAllUserInfo() {
		return userMapper.selectAllUserInfo();
	}

	/**
	 * User:删除用户
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	/**
	 * User:批量删除用户
	 */
	@Override
	public int deleteBatch(List<Integer> idList) {
		return userMapper.batchDelete(idList);
	}

	/**
	 * User:模糊搜索用户
	 */
	@Override
	public List<User> selectUserInfo(User user) {
		return userMapper.selectUserInfo(user);
	}
}
