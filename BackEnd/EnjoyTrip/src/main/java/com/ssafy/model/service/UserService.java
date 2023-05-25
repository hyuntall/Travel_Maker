package com.ssafy.model.service;

import java.sql.SQLException;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ssafy.dto.QnaDto;
import com.ssafy.dto.UserDto;



public interface UserService {
	public UserDto selectById(String id) throws SQLException;
	public int insertUser(UserDto user) throws SQLException;
	public int updateUserImg(UserDto userDto) throws SQLException;
	public UserDto login(UserDto userDto) throws Exception;
	public UserDto userInfo(String id) throws Exception;
	public void saveRefreshToken(String id, String refreshToken) throws Exception;
	public Object getRefreshToken(String id) throws Exception;
	public void deleRefreshToken(String id) throws Exception;
	public String getUserImg(String id) throws SQLException;
	public List<UserDto> searchByKeyword(String keyword) throws SQLException;
	public List<UserDto> searchNotFollowing(String keyword, String id) throws SQLException;
	public void follow(Map<String, String> map) throws SQLException;
	public List<UserDto> getFollowing(String id) throws SQLException; 
	public List<UserDto> getFollowingByKeyword(String id, String keyword) throws SQLException; 
	public List<UserDto> planDetail(int plan_idx) throws SQLException; 
	public List<UserDto> getFollower(String id) throws SQLException;
	public void unFollow(Map<String, String> map) throws SQLException;
	public void modifyUser(UserDto user) throws SQLException;
}
