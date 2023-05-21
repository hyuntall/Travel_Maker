package com.ssafy.model.repository;

import java.sql.SQLException;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ssafy.dto.QnaDto;
import com.ssafy.dto.UserDto;



@Mapper
public interface UserRepository {
	public UserDto selectById(String id) throws SQLException;
	public int insertUser(UserDto user) throws SQLException;
	public int checkUser(String id) throws SQLException;
	
	public int updateUserImg(UserDto userDto) throws SQLException;
	public UserDto login(UserDto userDto) throws SQLException;
	public UserDto userInfo(String id) throws SQLException;
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String id) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
}
