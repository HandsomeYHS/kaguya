package com.akanemurakawa.kaguya.dao.mapper;

import java.util.List;

import com.hanaeyuuma.freeblogs.model.Tag;

/**
 * 
 * @author HanaeYuuma
 * @date 2018-6-6
 */
public interface TagMapper {

	List<Tag> selectTagDistinct(Integer uid);

	int deleteByArticlePrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	int insert(Tag record);

	int insertSelective(Tag record);

	Tag selectByArticlePrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Tag record);

	int batchDeleteByArticlePrimaryKey(List<Integer> ids);

	int updateByPrimaryKey(Tag record);

	int updateName(Tag tag);
}