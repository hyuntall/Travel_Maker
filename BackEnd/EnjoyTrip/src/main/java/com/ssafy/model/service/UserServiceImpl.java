package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dto.UserDto;
import com.ssafy.model.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository urepo;

	@Override
	public UserDto selectById(String id) throws SQLException {
		return urepo.selectById(id);
	}

	@Override
	public int insertUser(UserDto user) throws SQLException {
		return urepo.insertUser(user);
	}

	@Override
	public UserDto login(UserDto userDto) throws Exception {
		if (userDto.getId() == null || userDto.getPassword() == null)
			return null;
		return urepo.login(userDto);
		
	}

	@Override
	public UserDto userInfo(String id) throws Exception {
		return urepo.userInfo(id);
	}

	@Override
	public void saveRefreshToken(String id, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("token", refreshToken);
		urepo.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String id) throws Exception {
		return urepo.getRefreshToken(id);
	}

	@Override
	public void deleRefreshToken(String id) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("token", null);
		urepo.deleteRefreshToken(map);
	}

	@Override
	public int updateUserImg(UserDto userDto) throws SQLException {
		return urepo.updateUserImg(userDto);
	}

	@Override
	public String getUserImg(String id) throws SQLException {
		return urepo.getUserImg(id);
	}

	@Override
	public List<UserDto> searchByKeyword(String keyword) throws SQLException {
		return urepo.searchByKeyword(keyword);
	}

	@Override
	public List<UserDto> searchNotFollowing(String keyword, String id) throws SQLException {
		return urepo.searchNotFollowing(keyword, id);
	}

	@Override
	public void follow(Map<String, String> map) throws SQLException {
		urepo.follow(map);
		
	}

	@Override
	public List<UserDto> getFollowing(String id) throws SQLException {
		return urepo.getFollowing(id);
	}

	@Override
	public List<UserDto> getFollowingByKeyword(String id, String keyword) throws SQLException {
		return urepo.getFollowingByKeyword(id, keyword);
	}

	@Override
	public List<UserDto> planDetail(int plan_idx) throws SQLException {
		return urepo.planDetail(plan_idx);
	}

	@Override
	public List<UserDto> getFollower(String id) throws SQLException {
		return urepo.getFollower(id);
	}

	@Override
	public void unFollow(Map<String, String> map) throws SQLException {
		urepo.unFollow(map);
	}

	@Override
	public void modifyUser(UserDto user) throws SQLException {
		urepo.modifyUser(user);
		
	};
}
