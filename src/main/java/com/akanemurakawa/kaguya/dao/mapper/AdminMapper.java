package com.akanemurakawa.kaguya.dao.mapper;

import com.akanemurakawa.kaguya.dao.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper<Admin> extends BaseMapper<Admin> {


	Admin selectByIdAndPssword(Admin admin);
}