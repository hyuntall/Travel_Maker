package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dto.CommentDto;
import com.ssafy.model.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	CommentRepository commentRepo;
	
	@Override
	public int insertComment(CommentDto comment) throws SQLException {
		return commentRepo.insertComment(comment);
	}

	@Override
	public int deleteComment(int idx) throws SQLException {
		return commentRepo.deleteComment(idx);
	}

	@Override
	public int modifyComment(CommentDto comment) throws SQLException {
		return commentRepo.modifyComment(comment);
	}

	@Override
	public List<CommentDto> selectCommentByBoard(int board_idx) throws SQLException {
		return commentRepo.selectCommentByBoard(board_idx);
	}

	@Override
	public List<CommentDto> selectCommentByUser(String user_id) throws SQLException {
		return commentRepo.selectCommentByUser(user_id);
	}
}
