package com.akanemurakawa.kaguya.dao.mapper;

import com.akanemurakawa.kaguya.dao.base.BaseMapper;
import com.akanemurakawa.kaguya.model.entity.Gallery;

public interface GalleryMapper extends BaseMapper<Gallery> {

    int deleteByPrimaryKey(Integer id);

    int insert(Gallery record);

    int insertSelective(Gallery record);

    Gallery selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Gallery record);

    int updateByPrimaryKey(Gallery record);
}