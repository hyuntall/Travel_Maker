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

import com.ssafy.dto.BoardDto;
import com.ssafy.model.service.BoardService;

@Controller
@RequestMapping("/board")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class BoardController {

	@Autowired
	private BoardService bsvc;

	@Autowired
	private ResourceLoader resLoader;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@GetMapping("/list")
	public ResponseEntity<?> selectBoard() throws SQLException {
		return new ResponseEntity<List<BoardDto>>(bsvc.selectBoard(), HttpStatus.OK);
	}

	@GetMapping("/list/{user_id}")
	public ResponseEntity<?> selectBoardByUser(@PathVariable String user_id) throws SQLException {
		return new ResponseEntity<List<BoardDto>>(bsvc.selectBoardByUser(user_id), HttpStatus.OK);
	}
	
	@GetMapping("list/page/{page}")
	public ResponseEntity<?> selectBoardByUser(@PathVariable int page) throws SQLException {
		System.out.println(page*5);
		return new ResponseEntity<List<BoardDto>>(bsvc.selectBoardPage(page*5), HttpStatus.OK);
	}
	
	@GetMapping("list/week/{page}")
	public ResponseEntity<?> selectWeekPopular(@PathVariable int page) throws SQLException {
		System.out.println(page*10);
		return new ResponseEntity<List<BoardDto>>(bsvc.selectWeekPopular(page*10), HttpStatus.OK);
	}
	
	@GetMapping("/{idx}")
	public ResponseEntity<?> selectBoardByIdx(@PathVariable int idx) throws SQLException {
		bsvc.addViewCount(idx);
		return new ResponseEntity<BoardDto>(bsvc.selectBoardByIdx(idx), HttpStatus.OK);
	}
	@GetMapping("/all/cnt")
	public ResponseEntity<?> selectBoardCount() throws SQLException {
		return new ResponseEntity<Integer>(bsvc.selectBoardCount(), HttpStatus.OK);
	}
	
	@PostMapping("/write")
	public ResponseEntity<?> insertBoard(@RequestParam("user_id") String user_id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestPart(required = false) MultipartFile image
            ) throws SQLException, IOException{
		BoardDto board = new BoardDto();
		System.out.println(user_id + " " + title + " " + content);
		if (image != null && image.getSize() > 0) {

			File imgDir = new File(uploadPath + "board/");
			if (!imgDir.exists()) {
				imgDir.mkdirs();
			}
			
			String imgFileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
			String imgFilePath = uploadPath + "board/" + imgFileName;
			File imgFile = new File(imgFilePath);
			

			board.setImage(imgFileName);
			image.transferTo(imgFile);
		}
		board.setUser_id(user_id);
		board.setTitle(title);
		board.setContent(content);
		// 게시글 DB에 저장
		return new ResponseEntity<Integer>(bsvc.insertBoard(board), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{idx}")
	public ResponseEntity<?> deleteBoard(@PathVariable int idx) throws SQLException {
		return new ResponseEntity<Integer>(bsvc.deleteBoard(idx), HttpStatus.OK);
	}
	
	@PutMapping("/update/{idx}")
	public ResponseEntity<?> updateBoard(@PathVariable int idx,
			@RequestParam("user_id") String user_id,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestPart(required = false) MultipartFile image
            ) throws SQLException, IOException{
		BoardDto board = new BoardDto();
		board.setIdx(idx);
		if (image != null && image.getSize() > 0) {

			File imgDir = new File(uploadPath + "board/");
			if (!imgDir.exists()) {
				imgDir.mkdirs();
			}
			
			String imgFileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
			String imgFilePath = uploadPath + "board/" + imgFileName;
			File imgFile = new File(imgFilePath);
			

			board.setImage(imgFileName);
			image.transferTo(imgFile);
		}
		board.setUser_id(user_id);
		board.setTitle(title);
		board.setContent(content);
		board.setUpdated_date(new Timestamp(System.currentTimeMillis() + 32400000));
		// 게시글 DB에 저장
		return new ResponseEntity<Integer>(bsvc.updateBoard(board), HttpStatus.OK);
	}
}
