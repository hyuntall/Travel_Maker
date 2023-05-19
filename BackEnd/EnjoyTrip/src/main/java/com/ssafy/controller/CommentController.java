package com.ssafy.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.dto.CommentDto;
import com.ssafy.model.service.CommentService;

@Controller
@RequestMapping("/comment")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class CommentController {

	@Autowired
	private CommentService csvc;

	
	@PostMapping("/write")
	public ResponseEntity<?> insertComment(@RequestBody CommentDto comment) throws SQLException, IOException{
		System.out.println(comment);
		return new ResponseEntity<Integer>(csvc.insertComment(comment), HttpStatus.OK);
	}
	
	@GetMapping("/board/{idx}")
	public ResponseEntity<?> selectCommentByBoard(@PathVariable int idx) throws SQLException {
		return new ResponseEntity<List<CommentDto>>(csvc.selectCommentByBoard(idx), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{idx}")
	public ResponseEntity<?> deleteComment(@PathVariable int idx) throws SQLException {
		return new ResponseEntity<Integer>(csvc.deleteComment(idx), HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateComment(@RequestBody CommentDto comment) throws SQLException, IOException{
		System.out.println(comment);
		comment.setUpdated_date(new Timestamp(System.currentTimeMillis() + 32400000));
		return new ResponseEntity<Integer>(csvc.modifyComment(comment), HttpStatus.OK);
	}

}
