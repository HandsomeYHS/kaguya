package com.akanemurakawa.kaguya.dao.internal.special;

import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.provider.base.BaseSelectProvider;

/**
 * 根据实体中的属性进行查询，只返回其中第一个值，没有则返回null，查询条件使用等号，目前仅支持支持limit语法的数据库引擎
 */
@tk.mybatis.mapper.annotation.RegisterMapper
public interface SelectLimitOneMapper<T> {

    /**
     * 根据实体中的属性进行查询，按limit范围查询，查询条件使用等号，目前仅支持支持limit语法的数据库引擎
     * @param record
     * @return
     */
    @SelectProvider(type = BaseSelectProvider.class, method = "dynamicSQL")
    T selectLimitOne(T record);
}
