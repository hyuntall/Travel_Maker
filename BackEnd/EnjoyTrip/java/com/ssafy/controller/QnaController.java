package com.ssafy.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.dto.QnaDto;
import com.ssafy.model.service.QnaService;
import com.ssafy.model.service.QnaServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/qna")
public class QnaController {
	
	@Autowired
	private QnaService qnasvc;
	
	@GetMapping("/list")
	public ResponseEntity<?> qnaList() throws SQLException {
		System.out.print("asdasdad");
		return new ResponseEntity<List<QnaDto>>(qnasvc.selectAll(), HttpStatus.OK);
	}
	
	@GetMapping("/detail")
	public ResponseEntity<?> detail(@PathVariable int id) throws SQLException {
		return new ResponseEntity<QnaDto>(qnasvc.selectById(id), HttpStatus.OK);
	}
}
