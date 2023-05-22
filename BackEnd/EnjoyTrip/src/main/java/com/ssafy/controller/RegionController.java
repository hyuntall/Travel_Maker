package com.ssafy.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.dto.RegionDto;
import com.ssafy.model.service.RegionService;
import com.ssafy.model.service.RegionServiceImpl;

@RestController
@RequestMapping("/region")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE })
public class RegionController {
	
	@Autowired
	private RegionServiceImpl regionService;
	
	@GetMapping("/all")
	public ResponseEntity<?> selectAll() throws SQLException {
		return new ResponseEntity<List<RegionDto>>(regionService.selectAll(), HttpStatus.OK);
	}
	
	@GetMapping("/cate1")
	public ResponseEntity<?> selectByCate1(@RequestBody String cate1) throws SQLException {
		return new ResponseEntity<List<RegionDto>>(regionService.selectABycate1(cate1), HttpStatus.OK);
	}
	
	@GetMapping("/keyword/{keyword}")
	public ResponseEntity<?> selectByKeyword(@PathVariable String keyword) throws SQLException {
		return new ResponseEntity<List<RegionDto>>(regionService.selectAByKeyword(keyword), HttpStatus.OK);
	}
}
