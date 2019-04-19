package com.akanemurakawa.kaguya.dao.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.akanemurakawa.kaguya.dao.base.BaseMapper;
import com.akanemurakawa.kaguya.model.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    List<Admin> selectByAcountAndPassword(@Param("record")Admin record);


}