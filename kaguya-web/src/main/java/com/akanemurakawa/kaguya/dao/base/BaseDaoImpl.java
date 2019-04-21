package com.akanemurakawa.kaguya.dao.base;

import java.util.List;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    protected abstract BaseMapper<T> getBaseMapper();

    @Override
    public List<T> list(T record) {
        return getBaseMapper().select(record);
    }

    @Override
    public T get(T record) {
        return getBaseMapper().selectOne(record);
    }

    @Override
    public int count(T record) {
        return getBaseMapper().selectCount(record);
    }

    @Override
    public int insert(T record) {
        return getBaseMapper().insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return getBaseMapper().insertSelective(record);
    }

    @Override
    public int insertList(List<T> recordList) {
        return getBaseMapper().insertList(recordList);
    }

    @Override
    public int insertSelectiveList(List<T> recordList) {
        return getBaseMapper().insertSelectiveList(recordList);
    }

    @Override
    public int delete(T record) {
        return getBaseMapper().delete(record);
    }

    @Override
    public T selectLimitOne(T record) {
        return getBaseMapper().selectLimitOne(record);
    }
}
