package com.akanemurakawa.kaguya.dao.impl;

import com.akanemurakawa.kaguya.dao.AdminDao;
import com.akanemurakawa.kaguya.dao.base.BaseDaoImpl;
import com.akanemurakawa.kaguya.dao.base.BaseMapper;
import com.akanemurakawa.kaguya.dao.mapper.AdminMapper;
import com.akanemurakawa.kaguya.model.entity.Admin;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

@Repository
public class AdminDaoImpl extends BaseDaoImpl<Admin> implements AdminDao{

    @Resource
    private AdminMapper adminMapper;

    @Override
    protected BaseMapper<Admin> getBaseMapper() {
        return adminMapper;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return getBaseMapper().deleteByPrimaryKey(id);
    }

    @Override
    public Admin selectByPrimaryKey(Integer id) {
        return getBaseMapper().selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Admin record) {
        return getBaseMapper().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Admin record) {
        return getBaseMapper().updateByPrimaryKey(record);
    }

    @Override
    public Admin selectByAcountAndPassword(Admin admin) {
        return getBaseMapper().selectByAcountAndPassword(admin);
    }

}
