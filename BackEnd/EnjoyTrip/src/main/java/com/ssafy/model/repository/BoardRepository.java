package com.ssafy.model.repository;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.BoardDto;


@Mapper
public interface BoardRepository {

	int insertBoard(BoardDto boardDto) throws SQLException;
	List<BoardDto> selectBoard() throws SQLException;
	List<BoardDto> selectBoardByUser(String user_id) throws SQLException;
	List<BoardDto> selectBoardPage(int page) throws SQLException;
	List<BoardDto> selectWeekPopular(int page) throws SQLException;
	BoardDto selectBoardByIdx(int idx) throws SQLException;
	int selectBoardCount() throws SQLException;
	int updateBoard(BoardDto boardDto) throws SQLException;
	int deleteBoard(int idx) throws SQLException;
	int addViewCount(int idx) throws SQLException;
}
 