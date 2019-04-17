package com.akanemurakawa.kaguya.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaeyuuma.freeblogs.dao.CategoryMapper;
import com.hanaeyuuma.freeblogs.model.Category;
import com.hanaeyuuma.freeblogs.service.CategoryService;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-6-10
 */
@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryMapper categoryMapper;

	/**
	 * Category:插入分类
	 */
	@Override
	public int insertSelective(Category record) {
		return categoryMapper.insertSelective(record);
	}

	/**
	 * Category:查找分类
	 */
	@Override
	public Category selectByArticlePrimaryKey(Integer id) {
		return categoryMapper.selectByArticlePrimaryKey(id);
	}

}
