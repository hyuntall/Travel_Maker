package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dto.BoardDto;
import com.ssafy.model.repository.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	BoardRepository boardRepo;
	
	@Override
	public int insertBoard(BoardDto boardDto) throws SQLException {
		return boardRepo.insertBoard(boardDto);
	}

	@Override
	public List<BoardDto> selectBoard() throws SQLException {
		return boardRepo.selectBoard();
	}

	@Override
	public BoardDto selectBoardByIdx(int idx) throws SQLException {
		return boardRepo.selectBoardByIdx(idx);
	}

	@Override
	public int updateBoard(BoardDto boardDto) throws SQLException {
		return boardRepo.updateBoard(boardDto);
	}

	@Override
	public int deleteBoard(int idx) throws SQLException {
		return boardRepo.deleteBoard(idx);
	}

}