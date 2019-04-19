package com.akanemurakawa.kaguya.dao;

import com.akanemurakawa.kaguya.dao.base.BaseDao;
import com.akanemurakawa.kaguya.model.entity.Admin;

public interface AdminDao extends BaseDao<Admin> {

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Admin selectByAcountAndPassword(Admin admin);
}
