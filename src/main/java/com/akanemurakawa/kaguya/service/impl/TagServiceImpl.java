package com.akanemurakawa.kaguya.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanaeyuuma.freeblogs.dao.TagMapper;
import com.hanaeyuuma.freeblogs.model.Tag;
import com.hanaeyuuma.freeblogs.service.TagService;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	private TagMapper tagMapper;

	/**
	 * Tag:查询所有的标签，不包括重复的
	 */
	@Override
	public List<Tag> selectTagDistinct(Integer uid) {
		return tagMapper.selectTagDistinct(uid);
	}

	/**
	 * Tag:插入标签
	 */
	@Override
	public int insertSelective(Tag record) {
		return tagMapper.insertSelective(record);
	}

	/**
	 * Tag:查询标签
	 */
	@Override
	public Tag selectByArticlePrimaryKey(Integer id) {
		return tagMapper.selectByArticlePrimaryKey(id);
	}

}
