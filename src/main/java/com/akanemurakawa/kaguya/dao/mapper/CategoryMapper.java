package com.akanemurakawa.kaguya.dao.mapper;

import com.akanemurakawa.kaguya.dao.base.BaseMapper;
import com.akanemurakawa.kaguya.model.entity.Admin;
import com.akanemurakawa.kaguya.model.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Admin> {

	int deleteByArticlePrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	int insert(Category record);

	int insertSelective(Category record);

	Category selectByArticlePrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Category record);

	int updateByPrimaryKey(Category record);

	int batchDeleteByArticlePrimaryKey(List<Integer> ids);

	int updateName(Category category);
}