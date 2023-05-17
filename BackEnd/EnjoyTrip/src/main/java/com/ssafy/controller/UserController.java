package com.ssafy.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.dto.UserDto;
import com.ssafy.model.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService usvc;
	
	@GetMapping("/{idx}")
	public ResponseEntity<?> selectByIdx(@PathVariable int idx) throws SQLException {
		return new ResponseEntity<UserDto>(usvc.selectByIdx(idx),HttpStatus.OK);
	}
}
