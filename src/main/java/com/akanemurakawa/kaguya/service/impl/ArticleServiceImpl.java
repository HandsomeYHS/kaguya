package com.akanemurakawa.kaguya.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaeyuuma.freeblogs.dao.ArticleMapper;
import com.hanaeyuuma.freeblogs.dao.CategoryMapper;
import com.hanaeyuuma.freeblogs.dao.TagMapper;
import com.hanaeyuuma.freeblogs.model.Article;
import com.hanaeyuuma.freeblogs.model.Category;
import com.hanaeyuuma.freeblogs.model.Tag;
import com.hanaeyuuma.freeblogs.service.ArticleService;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-6-10
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private TagMapper tagMapper;

	/**
	 * Article:查询用户所有文章，并带有文章的分类和用户信息
	 */
	public List<Article> getUserAllArticle(Integer uid) {
		return articleMapper.selectWithCategoryAndUser(uid);
	}

	/**
	 * Article:查看所有文章，并带有文章的分类和用户的信息
	 */
	@Override
	public List<Article> getAllArticle() {
		return articleMapper.selectAllWithCategoryAndUser();
	}

	/**
	 * Article:用户近期文章，查询最近的三篇文章
	 */
	@Override
	public List<Article> selectRecentArticles(Integer uid) {
		return articleMapper.selectRecentArticles(uid);
	}

	/**
	 * Article:查看用户热门文章
	 */
	@Override
	public List<Article> selectHotArticles(Integer uid) {
		return articleMapper.selectHotArticles(uid);
	}

	/**
	 * Article:查询用户所有文章的基本信息，不带用户和分类，标签，结果通过时间降序
	 */
	@Override
	public List<Article> selectAllBaseInfo(Integer uid) {
		return articleMapper.selectAllBaseInfo(uid);
	}

	/**
	 * Article:通过标题获得文章内容，这里目的是用于取出在线文档的内容
	 */
	@Override
	public String selectContentByTitle(String title) {
		return articleMapper.selectContentByTitle(title);
	}

	/**
	 * Article:通过查询id获得文章的所有信息，目的是用于点击阅读更多的时候，显示文章的内容
	 */
	@Override
	public Article selectByPrimaryKey(Integer id) {
		return articleMapper.selectByPrimaryKey(id);
	}

	/**
	 * Article:通过id查找标题
	 */
	@Override
	public String selectTitleByPrimaryKey(Integer id) {
		return articleMapper.selectTitleByPrimaryKey(id);
	}

	/**
	 * Article:添加文章
	 */
	@Override
	public int insert(Article record) {
		return articleMapper.insert(record);
	}

	/**
	 * Article:删除文章 1.首先删除分类和标签 2.再删除文章
	 * 这里通过设置数据库的外键属性，删除时：CASCADE。
	 * 当删除父表的时候，会检查该记录是否有外键，如果有则删除对应的子表记录。
	 */
	@Override
	public int deleteArticleByPrimaryKey(Integer id) {
		articleMapper.deleteByPrimaryKey(id);
		return 0;
	}

	/**
	 * Article: 批量删除
	 * 这里通过设置数据库的外键属性，删除时：CASCADE。
	 * 当删除父表的时候，会检查该记录是否有外键，如果有则删除对应的子表记录。
	 */
	@Override
	public int deleteBatch(List<Integer> ids) {
		// delete from ? where id in (?,?,...,?);
		articleMapper.batchDeleteByPrimaryKey(ids);
		return 0;
	}

	/**
	 * Article:查询文章的标题和内容
	 */
	@Override
	public Article selectTitleAndContentByPrimaryKey(Integer id) {
		return articleMapper.selectTitleAndContentByPrimaryKey(id);
	}

	/**
	 * Article:修改文章
	 */
	@Override
	public int updateArticleByPrimaryKey(Article article) {
		Category newCategory = new Category(null, article.getId(), null,
				article.getArticleCategory().getCategoryName());
		categoryMapper.updateName(newCategory);
		Tag newTag = new Tag(null, article.getId(), null, article.getArticleTag().getTagName());
		tagMapper.updateName(newTag);
		Article newArticle = new Article(article.getId(), article.getArticleTitle(), article.getArticleSumary(),
				article.getArticleContent());
		articleMapper.updateByPrimaryKeySelective(newArticle);
		return 0;
	}

	/**
	 * Article:通过id查询文章基本信息
	 */
	@Override
	public Article selectBaseByPrimaryKey(Integer id) {
		return articleMapper.selectBaseByPrimaryKey(id);
	}

	/**
	 * Article:查询前一篇文章
	 */
	@Override
	public Article selectPreTitleByPrimaryKey(Article record) {
		return articleMapper.selectPreTitleByPrimaryKey(record);
	}

	/**
	 * Article:查询后一篇文章
	 */
	@Override
	public Article selectNextTitleByPrimaryKey(Article record) {
		return articleMapper.selectNextTitleByPrimaryKey(record);
	}

	/**
	 * Article:查询最小的id
	 */
	@Override
	public Integer selectMinIdByUserId(Integer uid) {
		return articleMapper.selectMinIdByUserId(uid);
	}

	/**
	 * Article:查询最大的id
	 */
	@Override
	public Integer selectMaxIdByUserId(Integer uid) {
		return articleMapper.selectMaxIdByUserId(uid);
	}

	/**
	 * Article:更新文章的阅读人数
	 */
	@Override
	public Integer updateHitByPrimaryKey(Integer id) {
		return articleMapper.updateHitByPrimaryKey(id);
	}

	/**
	 * Article:获得所有关注的人的文章
	 */
	@Override
	public List<Article> getAllFollowingArticle(List<Integer> uids) {
		return articleMapper.batchSelectByUserIds(uids);
	}

	/**
	 * Article:模糊查询
	 */
	@Override
	public List<Article> search(String value) {
		return articleMapper.search(value);
	}

}
