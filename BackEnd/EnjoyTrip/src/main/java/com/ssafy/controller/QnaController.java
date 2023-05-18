package com.ssafy.controller;

import java.sql.SQLException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.dto.QnaDto;
import com.ssafy.model.service.QnaService;
import com.ssafy.model.service.QnaServiceImpl;
import com.ssafy.model.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/qna")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.DELETE })
public class QnaController {
	
	@Autowired
	private QnaService qnasvc;
	
	@Autowired
	private UserService usvc;
	
	@GetMapping("/list")
	public ResponseEntity<?> qnaList() throws SQLException {
		return new ResponseEntity<List<QnaDto>>(qnasvc.selectAll(), HttpStatus.OK);
	}
	
	@GetMapping("/detail/{idx}")
	public ResponseEntity<?> detail(@PathVariable int idx) throws SQLException {
		return new ResponseEntity<QnaDto>(qnasvc.selectById(idx), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{idx}")
	public ResponseEntity<?> delete(@PathVariable int idx) throws SQLException {
		qnasvc.delete(idx);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@RequestBody QnaDto dto) throws SQLException {
		qnasvc.update(dto);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@PostMapping("/write")
	public ResponseEntity<?> write(@RequestBody QnaDto dto) throws SQLException{
		qnasvc.insert(dto);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
