package com.akanemurakawa.kaguya.dao.mapper;

import java.util.List;

import com.akanemurakawa.kaguya.dao.base.BaseMapper;
import com.akanemurakawa.kaguya.model.entity.Admin;
import com.akanemurakawa.kaguya.model.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Admin> {

	List<Article> selectWithCategoryAndUser(Integer uid);

	List<Article> selectAllWithCategoryAndUser();

	List<Article> selectRecentArticles(Integer uid);

	List<Article> selectHotArticles(Integer uid);

	List<Article> selectAllBaseInfo(Integer uid);

	Article selectBaseByPrimaryKey(Integer id);

	String selectContentByTitle(String title);

	Article selectByPrimaryKey(Integer id);

	String selectTitleByPrimaryKey(Integer id);

	Article selectPreTitleByPrimaryKey(Article record);

	Article selectNextTitleByPrimaryKey(Article record);

	Integer selectMinIdByUserId(Integer uid);

	Integer selectMaxIdByUserId(Integer uid);

	Article selectTitleAndContentByPrimaryKey(Integer id);

	Integer updateHitByPrimaryKey(Integer id);

	int insert(Article record);

	int batchDeleteByPrimaryKey(List<Integer> ids);

	int deleteByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Article record);

	int insertSelective(Article record);

	int updateByPrimaryKeyWithBLOBs(Article record);

	int updateByPrimaryKey(Article record);

	List<Article> batchSelectByUserIds(List<Integer> uids);
	
	List<Article> search(String value);
}