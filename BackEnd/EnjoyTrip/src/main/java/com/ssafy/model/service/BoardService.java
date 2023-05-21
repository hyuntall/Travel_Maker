package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.BoardDto;

public interface BoardService {
	int insertBoard(BoardDto boardDto) throws SQLException;
	List<BoardDto> selectBoard() throws SQLException;
	List<BoardDto> selectBoardByUser(String user_id) throws SQLException;
	BoardDto selectBoardByIdx(int idx) throws SQLException;	
	int updateBoard(BoardDto boardDto) throws SQLException;
	int deleteBoard(int idx) throws SQLException;
	int addViewCount(int idx) throws SQLException;
}
 