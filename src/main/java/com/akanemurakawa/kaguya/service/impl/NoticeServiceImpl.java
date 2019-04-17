package com.akanemurakawa.kaguya.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaeyuuma.freeblogs.dao.NoticeMapper;
import com.hanaeyuuma.freeblogs.model.Notice;
import com.hanaeyuuma.freeblogs.service.NoticeService;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-6-10
 */
@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	private NoticeMapper noticeMapper;

	/**
	 * Notice:查询最近的第一条公告
	 */
	@Override
	public Notice selectRecentOneOrderByDate() {
		return noticeMapper.selectRecentOneOrderByDate();
	}

	/**
	 * Notice:查看所有的公告，用户后台管理，用户滑到底部时候使用ajax分页查看
	 */
	@Override
	public List<Notice> selectAllWithAdmin() {
		return noticeMapper.selectAllWithAdmin();
	}

	/**
	 * Notice:查看公告
	 */
	@Override
	public Notice selectByPrimaryKey(Integer id) {
		return noticeMapper.selectByPrimaryKey(id);
	}

	/**
	 * Notice:删除公告
	 */
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return noticeMapper.deleteByPrimaryKey(id);
	}

	/**
	 * Notice:批量删除
	 */
	@Override
	public int deleteBatch(List<Integer> ids) {
		return noticeMapper.batchDeleteByPrimaryKey(ids);
	}

	/**
	 * Notice:检查标题是否存在
	 */
	@Override
	public Integer checkExistTitle(String title) {
		return noticeMapper.selectIdByTitle(title);
	}

	/**
	 * Notice:插入公告
	 */
	@Override
	public int insert(Notice record) {
		return noticeMapper.insert(record);
	}

	/**
	 * Notice:通过id查询标题
	 */
	@Override
	public String selectTitleById(Integer id) {
		return noticeMapper.selectTitleById(id);
	}

	/**
	 * Notice:更新公告
	 */
	@Override
	public int updateByPrimaryKey(Notice record) {
		return noticeMapper.updateByPrimaryKey(record);
	}
}
