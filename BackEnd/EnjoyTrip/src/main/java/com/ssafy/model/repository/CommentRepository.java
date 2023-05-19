package com.ssafy.model.repository;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.CommentDto;

@Mapper
public interface CommentRepository {

	int insertComment(CommentDto comment) throws SQLException;
	int deleteComment(int idx) throws SQLException;
	int modifyComment(CommentDto comment) throws SQLException;
	List<CommentDto> selectCommentByBoard(int board_idx) throws SQLException;
	List<CommentDto> selectCommentByUser(String user_id) throws SQLException;
}
 