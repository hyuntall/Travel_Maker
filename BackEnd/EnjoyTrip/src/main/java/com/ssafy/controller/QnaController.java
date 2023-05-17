package com.ssafy.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
		return new ResponseEntity<List<QnaDto>>(qnasvc.selectAll(), HttpStatus.OK);
	}
	
	@GetMapping("/detail/{idx}")
	public ResponseEntity<?> detail(@PathVariable int idx) throws SQLException {
		return new ResponseEntity<QnaDto>(qnasvc.selectById(idx), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{idx}")
	public void delete(@PathVariable int idx) throws SQLException {
		qnasvc.delete(idx);
	}
	
	@PutMapping("/update")
	public void update(@RequestBody QnaDto dto) throws SQLException {
		qnasvc.update(dto);
	}
	
	@PostMapping("/write")
	public void write(@RequestBody QnaDto dto) throws SQLException{
		System.out.println("ddddddd");
		qnasvc.insert(dto);
	}
}
