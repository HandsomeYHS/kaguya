package com.akanemurakawa.kaguya.dao.base;

import java.util.List;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    protected abstract BaseMapper<T> getBaseMapper();

    @Override
    public List<T> select(T t) {
        return getBaseMapper().select(t);
    }

    @Override
    public int selectCount(T t) {
        return getBaseMapper().selectCount(t);
    }

    @Override
    public int insert(T t) {
        return getBaseMapper().insert(t);
    }

    @Override
    public int insertSelective(T t) {
        return getBaseMapper().insertSelective(t);
    }

    @Override
    public int insertList(List<T> list) {
        return getBaseMapper().insertList(list);
    }

    @Override
    public int delete(T t) {
        return getBaseMapper().delete(t);
    }
}
