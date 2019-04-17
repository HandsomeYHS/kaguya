package com.akanemurakawa.kaguya.dao.mapper;

import com.akanemurakawa.kaguya.dao.base.BaseMapper;
import com.akanemurakawa.kaguya.model.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {


	int deleteByPrimaryKey(Integer id);

	int insert(Admin record);

	int insertSelective(Admin record);

	Admin selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Admin record);

	int updateByPrimaryKey(Admin record);

	Admin selectByIdAndPassword(Admin admin);
}