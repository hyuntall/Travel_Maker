package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.QnaDto;

public interface QnaService {
	public List<QnaDto> selectAll() throws SQLException;
	public void insert(QnaDto dto) throws SQLException; 
	public void update(QnaDto dto) throws SQLException;
	public QnaDto selectById(int id) throws SQLException;
	public void delete(int id) throws SQLException;
}
