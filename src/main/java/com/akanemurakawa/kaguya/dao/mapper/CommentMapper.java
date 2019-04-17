package com.akanemurakawa.kaguya.dao.mapper;

import java.util.List;

import com.akanemurakawa.kaguya.dao.base.BaseMapper;
import com.akanemurakawa.kaguya.model.entity.Admin;
import com.akanemurakawa.kaguya.model.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<Admin> {

	List<Comment> selectComment(Integer articleId);

	int insert(Comment record);

	int delete(Integer id);

}
