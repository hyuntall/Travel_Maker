package com.ssafy.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ssafy.model.service.JwtServiceImpl;
import com.ssafy.model.service.UserService;



@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,RequestMethod.DELETE })
public class UserController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
			
	@Autowired
	private UserService usvc;
	
	@Autowired
	private JwtServiceImpl jwtService;
	
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
//	@PostMapping("/login")
//	public ResponseEntity<?> loginUser(@RequestBody UserDto user, HttpSession session) throws SQLException {
//		UserDto u = usvc.selectById(user.getId());
//		if (u != null && u.getPassword().equals(user.getPassword())) {
//			session.setAttribute("userId", user.getId());
//			return new ResponseEntity<String>("로그인 성공", HttpStatus.OK);
//		}
//		return new ResponseEntity<String>("로그인 실패", HttpStatus.BAD_REQUEST);
//	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody UserDto userDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			UserDto loginUser = usvc.login(userDto);
			if (loginUser != null) {
				String accessToken = jwtService.createAccessToken("userid", loginUser.getId());// key, data
				String refreshToken = jwtService.createRefreshToken("userid", loginUser.getId());// key, data
				usvc.saveRefreshToken(userDto.getId(), refreshToken);
				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@GetMapping("/info/{id}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("id") String id,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				UserDto userDto = usvc.userInfo(id);
				resultMap.put("userInfo", userDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	

	@GetMapping("/logout/{id}")
	public ResponseEntity<?> removeToken(@PathVariable("id") String id) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			usvc.deleRefreshToken(id);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}
	
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody UserDto userDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, userDto : {}", token, userDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(usvc.getRefreshToken(userDto.getId()))) {
				String accessToken = jwtService.createAccessToken("userid", userDto.getId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
