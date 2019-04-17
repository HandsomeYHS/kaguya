package com.akanemurakawa.kaguya.service;

import java.util.List;

import com.hanaeyuuma.freeblogs.model.Article;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-6-10
 */
public interface ArticleService {
	
	List<Article> getUserAllArticle(Integer uid);
	
	List<Article> getAllArticle();
	
	List<Article> selectRecentArticles(Integer uid);
	
	List<Article> selectHotArticles(Integer uid);
	
	List<Article> selectAllBaseInfo(Integer uid);
	
	Integer selectMinIdByUserId(Integer uid);
	
	Integer selectMaxIdByUserId(Integer uid);
	
	String selectContentByTitle(String title);
	
	Article selectByPrimaryKey(Integer id);
	
	Article selectBaseByPrimaryKey(Integer id);
	
	String selectTitleByPrimaryKey(Integer id);
	
    Article selectPreTitleByPrimaryKey(Article record);
    
    Article selectNextTitleByPrimaryKey(Article record);
	
	int insert(Article record);
	
	Integer updateHitByPrimaryKey(Integer id);
	
	int deleteArticleByPrimaryKey(Integer id);
	
	int deleteBatch(List<Integer> ids);
	
	Article selectTitleAndContentByPrimaryKey(Integer id);
	
	int updateArticleByPrimaryKey(Article article);
	
	List<Article> getAllFollowingArticle(List<Integer> uids);
	
	List<Article> search(String value);
}
