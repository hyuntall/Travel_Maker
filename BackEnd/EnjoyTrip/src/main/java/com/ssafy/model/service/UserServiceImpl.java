package com.ssafy.model.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dto.UserDto;
import com.ssafy.model.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository urepo;
	
	public UserDto selectByIdx(int idx) throws SQLException {
		return urepo.selectByIdx(idx);
	}

	@Override
	public UserDto selectById(String id) throws SQLException {
		// TODO Auto-generated method stub
		return urepo.selectById(id);
	}

	@Override
	public int insertUser(UserDto user) throws SQLException {
		// TODO Auto-generated method stub
		return urepo.insertUser(user);
	};
}
