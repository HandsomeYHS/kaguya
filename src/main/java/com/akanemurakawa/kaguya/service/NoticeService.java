package com.akanemurakawa.kaguya.service;

import java.util.List;

import com.hanaeyuuma.freeblogs.model.Notice;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-6-10
 */
public interface NoticeService {

	Notice selectRecentOneOrderByDate();
	
	List<Notice> selectAllWithAdmin();
	
	Notice selectByPrimaryKey(Integer id);
	
	int deleteByPrimaryKey(Integer id);
	
	int deleteBatch(List<Integer> ids);
	
	Integer checkExistTitle(String title);
	
	int insert(Notice record);
	 
	String selectTitleById(Integer id);
	
	 int updateByPrimaryKey(Notice record);
}
