package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.CommentDto;

public interface CommentService  {
	int insertComment(CommentDto comment) throws SQLException;
	int deleteComment(int idx) throws SQLException;
	int modifyComment(CommentDto comment) throws SQLException;
	List<CommentDto> selectCommentByBoard(int board_idx) throws SQLException;
	List<CommentDto> selectCommentByUser(String user_id) throws SQLException;
}
