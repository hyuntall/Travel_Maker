package com.ssafy.model.repository;

import java.sql.SQLException;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ssafy.dto.QnaDto;


@Mapper
public interface QnaRepository {
	public List<QnaDto> selectAll() throws SQLException;
	public void insert(QnaDto dto) throws SQLException; 
	public void update(QnaDto dto) throws SQLException;
	public QnaDto selectById(int id) throws SQLException;
	public void delete(int id) throws SQLException;
}
