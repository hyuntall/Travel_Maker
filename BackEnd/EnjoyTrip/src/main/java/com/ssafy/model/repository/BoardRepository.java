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
	BoardDto selectBoardByIdx(int idx) throws SQLException;
	int updateBoard(BoardDto boardDto) throws SQLException;
	int deleteBoard(int idx) throws SQLException;
	int addViewCount(int idx) throws SQLException;
}
 