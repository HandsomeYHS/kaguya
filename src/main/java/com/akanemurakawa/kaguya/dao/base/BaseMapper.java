package com.akanemurakawa.kaguya.dao.base;

import com.akanemurakawa.kaguya.dao.internal.special.InsertSelectiveListMapper;
import com.akanemurakawa.kaguya.dao.internal.special.SelectLimitOneMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 基础公用的mapper
 *
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>, SelectLimitOneMapper<T>,
        InsertSelectiveListMapper<T> {
}
