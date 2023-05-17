package com.ssafy.model.repository;

import java.sql.SQLException;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ssafy.dto.QnaDto;
import com.ssafy.dto.UserDto;


@Mapper
public interface UserRepository {
	public UserDto selectByIdx(int idx) throws SQLException;
	public UserDto selectById(String id) throws SQLException;
}
