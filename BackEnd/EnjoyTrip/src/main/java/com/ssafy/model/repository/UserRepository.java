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
	public String getUserImg(String id) throws SQLException;
	public int updateUserImg(UserDto userDto) throws SQLException;
	public UserDto login(UserDto userDto) throws SQLException;
	public UserDto userInfo(String id) throws SQLException;
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String id) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
	public List<UserDto> searchByKeyword(String keyword) throws SQLException;
	//내가 팔로잉하고 있는 사람을 제외한 나머지 유저리스트
	public List<UserDto> searchNotFollowing(String keyword, String id) throws SQLException; 
	public void follow(Map<String, String> map) throws SQLException;
	public List<UserDto> getFollowing(String id) throws SQLException; 
}
