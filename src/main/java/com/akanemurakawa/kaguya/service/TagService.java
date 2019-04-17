package com.akanemurakawa.kaguya.service;

import java.util.List;

import com.hanaeyuuma.freeblogs.model.Tag;

public interface TagService {
	
	 List<Tag> selectTagDistinct(Integer uid);
	 
	 int insertSelective(Tag record);
	 
	 Tag selectByArticlePrimaryKey(Integer id);
}
