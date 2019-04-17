package com.akanemurakawa.kaguya.dao.mapper;

import java.util.List;

import com.hanaeyuuma.freeblogs.model.Notice;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-6-6
 */
public interface NoticeMapper {

	Notice selectRecentOneOrderByDate();

	List<Notice> selectAllWithAdmin();

	Notice selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	int batchDeleteByPrimaryKey(List<Integer> ids);

	int insert(Notice record);

	int insertSelective(Notice record);

	int updateByPrimaryKeySelective(Notice record);

	int updateByPrimaryKeyWithBLOBs(Notice record);

	int updateByPrimaryKey(Notice record);

	Integer selectIdByTitle(String title);

	String selectTitleById(Integer id);

}