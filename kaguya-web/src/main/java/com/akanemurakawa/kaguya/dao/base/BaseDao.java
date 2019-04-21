package com.akanemurakawa.kaguya.dao.base;

import java.util.List;

public interface BaseDao<T> {

    List<T> list(T record);

    T get(T record);

    int count(T record);

    int insert(T record);

    int insertSelective(T record);

    int insertList(List<T> recordList);

    int insertSelectiveList(List<T> recordList);

    int delete(T record);

    T selectLimitOne(T record);
}
