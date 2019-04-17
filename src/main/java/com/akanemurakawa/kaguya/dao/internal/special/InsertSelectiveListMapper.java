package com.akanemurakawa.kaguya.dao.internal.special;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import java.util.List;
import com.akanemurakawa.kaguya.dao.internal.provider.*;

@tk.mybatis.mapper.annotation.RegisterMapper
public interface InsertSelectiveListMapper<T> {

    /**
     * 批量插入实体列表，实体中null的属性不会保存，
     * 支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含id属性并且必须为自增列。
     *
     * @param recordList 需要插入的实体列表
     * @return 插入的条数
     */
    @Options(useGeneratedKeys = true, keyProperty = "tid")
    @InsertProvider(type = InsertSelectiveListProvider.class, method = "dynamicSQL")
    int insertSelectiveList(List<T> recordList);
}
