package com.akanemurakawa.kaguya.dao.base;

import java.util.List;

public interface BaseDao<T>{

    List<T> select(T t);

    int selectCount(T t);

    int insert(T t);

    int insertSelective(T t);

    int insertList(List<T> list);

    int delete(T t);

}
