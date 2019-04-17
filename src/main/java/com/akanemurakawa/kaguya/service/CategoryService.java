package com.akanemurakawa.kaguya.service;

import com.hanaeyuuma.freeblogs.model.Category;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-6-10
 */
public interface CategoryService {

	int insertSelective(Category record);
	
	Category selectByArticlePrimaryKey(Integer id);
}
