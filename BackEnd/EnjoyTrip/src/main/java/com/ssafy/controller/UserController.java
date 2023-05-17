package com.ssafy.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.dto.UserDto;
import com.ssafy.model.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE })
public class UserController {
	
	@Autowired
	private UserService usvc;
	
	@GetMapping("/{idx}")
	public ResponseEntity<?> selectByIdx(@PathVariable int idx) throws SQLException {
		return new ResponseEntity<UserDto>(usvc.selectByIdx(idx),HttpStatus.OK);
	}
	@PostMapping("/regist")
	public ResponseEntity<?> insertUser(@RequestBody UserDto user) throws SQLException {
		UserDto u = usvc.selectById(user.getId());
		if (u == null) {
			usvc.insertUser(user);
			return new ResponseEntity<String>("회원가입 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("이미 존재하는 계정입니다.", HttpStatus.BAD_REQUEST);
	}
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody UserDto user, HttpSession session) throws SQLException {
		UserDto u = usvc.selectById(user.getId());
		if (u != null && u.getPassword().equals(user.getPassword())) {
			session.setAttribute("userId", user.getId());
			return new ResponseEntity<String>("로그인 성공", HttpStatus.OK);
		}
		return new ResponseEntity<String>("로그인 실패", HttpStatus.BAD_REQUEST);
	}
}
