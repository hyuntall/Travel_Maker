package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.HashMap;
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
		// TODO Auto-generated method stub
		return urepo.selectById(id);
	}

	@Override
	public int insertUser(UserDto user) throws SQLException {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return urepo.getRefreshToken(id);
	}

	@Override
	public void deleRefreshToken(String id) throws Exception {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("token", null);
		urepo.deleteRefreshToken(map);
	}

	@Override
	public int updateUserImg(UserDto userDto) throws SQLException {
		// TODO Auto-generated method stub
		return urepo.updateUserImg(userDto);
	}

	@Override
	public String getUserImg(String id) throws SQLException {
		// TODO Auto-generated method stub
		return urepo.getUserImg(id);
	};
}
