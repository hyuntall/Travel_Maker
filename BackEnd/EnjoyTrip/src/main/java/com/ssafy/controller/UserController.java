package com.ssafy.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	@Autowired
	private ResourceLoader resLoader;
	
	@Value("${upload.path}")
	private String uploadPath;
	
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
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody UserDto userDto) {
		System.out.println(userDto);
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
		System.out.println(id);
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
	
	@PostMapping("/image")
	public ResponseEntity<?> updateImg(@RequestParam("user_id") String user_id, 
			@RequestPart(required = false) MultipartFile image) throws IllegalStateException, IOException, SQLException {
		UserDto user = new UserDto();
		user.setId(user_id);
			if (image != null && image.getSize() > 0) {
	
				File imgDir = new File(uploadPath + "user/");
				if (!imgDir.exists()) {
					imgDir.mkdirs();
				}
				
				String imgFileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
				String imgFilePath = uploadPath + "user/" + imgFileName;
				File imgFile = new File(imgFilePath);
				user.setImage(imgFileName);
				image.transferTo(imgFile);
				usvc.updateUserImg(user);
				return new ResponseEntity<String>(imgFileName, HttpStatus.OK);
			}
			return new ResponseEntity<String>("이미지 변경 실패", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/image/{user_id}")
	public ResponseEntity<?> getProfileImg(@PathVariable String user_id) throws SQLException {
		return new ResponseEntity<String>(usvc.getUserImg(user_id), HttpStatus.OK);
		
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
	
	@GetMapping("/searchBykeyword/{keyword}")
	public ResponseEntity<?> searchByKeyword(@PathVariable String keyword) throws SQLException {
		// TODO Auto-generated method stub
		return new ResponseEntity<List<UserDto>>(usvc.searchByKeyword(keyword),HttpStatus.OK);
	};
	
	@GetMapping("/searchBykeyword/{keyword}/{id}")
	public ResponseEntity<?> searchNotFollowing(@PathVariable String keyword, @PathVariable String id) throws SQLException {
		System.out.println(keyword);
		System.out.println(id);
		return new ResponseEntity<List<UserDto>>(usvc.searchNotFollowing(keyword, id),HttpStatus.OK);
	};
	
	@PostMapping("/follow")
	public ResponseEntity<?> follow(@RequestBody Map<String, String> params) throws SQLException {
		usvc.follow(params);
		return new ResponseEntity<String>("팔로잉 성공", HttpStatus.OK);
		
	}
	
	@GetMapping("/follow/{id}")
	public ResponseEntity<?> getFollowing(@PathVariable String id) throws SQLException {
		System.out.println(id);
		//사용자 아이디 -> 팔로워 아이디 조회 -> 팔로워 리스트 반환
		return new ResponseEntity<List<UserDto>>(usvc.getFollowing(id), HttpStatus.OK);
		
	}
	
	@GetMapping("/follower/{id}")
	public ResponseEntity<?> getFollower(@PathVariable String id) throws SQLException {
		System.out.println(id);
		//사용자 아이디 -> 팔로워 아이디 조회 -> 팔로워 리스트 반환
		return new ResponseEntity<List<UserDto>>(usvc.getFollower(id), HttpStatus.OK);
		
	}
	
	@GetMapping("/friend/{id}/{keyword}")
	public ResponseEntity<?> getFollowingByKeyword(@PathVariable String id, @PathVariable String keyword) throws SQLException {
//		System.out.println(id);
//		System.out.println(keyword);
		
		//사용자 아이디, 키워드로 친구목록 가져오기
		return new ResponseEntity<List<UserDto>>(usvc.getFollowingByKeyword(id, keyword), HttpStatus.OK);
	}
	@PostMapping("/unfollow")
	public ResponseEntity<?> unFollow(@RequestBody Map<String, String> params) throws SQLException {
		usvc.unFollow(params);
		return new ResponseEntity<String>("팔로우 취소 성공", HttpStatus.OK);
	}
	
	@PutMapping("/modify")
	public ResponseEntity<?> modifyUser(@RequestBody UserDto user) throws SQLException {
//		System.out.println("modifyUser: "+user);
		usvc.modifyUser(user);
		return new ResponseEntity<String>("회원정보 수정 성공", HttpStatus.OK);
	}
}
