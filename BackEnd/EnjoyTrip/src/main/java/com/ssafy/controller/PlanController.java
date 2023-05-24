package com.ssafy.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssafy.dto.PlaceDto;
import com.ssafy.dto.PlanCommentDto;
import com.ssafy.dto.PlanDto;
import com.ssafy.dto.TripMemberDto;
import com.ssafy.model.service.PlaceService;
import com.ssafy.model.service.PlanCommentService;
import com.ssafy.model.service.PlanService;
import com.ssafy.model.service.TripMemberService;
import com.ssafy.model.service.UserService;

@Controller
@RequestMapping("/plan")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class PlanController {
	@Autowired
	PlaceService placeSvc;
	
	@Autowired
	PlanService planSvc;
	
	@Autowired
	TripMemberService tmSvc;
	
	@Autowired
	UserService userSvc;
	
	@Autowired
	PlanCommentService pcSvc;
	
	@PostMapping("/insert")
	public ResponseEntity<?> insertPlan(@RequestBody PlanDto plan) throws SQLException {
		//1. 받아온 pl
		int planIdx = planSvc.makePlan(plan);

		if (planIdx > 0 && plan.getPlaces() != null) {
			List<String> users = plan.getUser_id();
			for (String user_id : users) {
				System.out.println(user_id + "dsadasdasdsa");
				TripMemberDto tripMember = new TripMemberDto(planIdx, user_id);
				tmSvc.insertUserOfPlan(tripMember);
			}
			for (PlaceDto place : plan.getPlaces()) {
				place.setPlan_idx(planIdx);
				System.out.println(place);
				placeSvc.insertPlace(place);
			}
			return new ResponseEntity<Integer>(1, HttpStatus.OK);
		}
		return new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/list/{user_id}")
	public ResponseEntity<?> selectByuser(@PathVariable String user_id) throws SQLException {
		List<Integer> plan_idxs = tmSvc.selectPlanIdx(user_id);
		List<PlanDto> plans = new ArrayList<PlanDto>();
		for (int plan_idx : plan_idxs) {
			plans.add(planSvc.selectByIdx(plan_idx));
		}
		return new ResponseEntity<List<PlanDto>>(plans, HttpStatus.OK);
	}
	
	@GetMapping("/detail/{plan_idx}")
	public ResponseEntity<?> planDetail(@PathVariable int plan_idx) throws SQLException {
		System.out.println(plan_idx);
		HashMap<String, Object> map = new HashMap<String, Object>();
		//1.plan 조회
		PlanDto plan = planSvc.selectByIdx(plan_idx);
		map.put("plan", plan);

		//2.tripmember 조회 -> user table user 조회
		//select * from user where id in (select user_id from tripmember where plan_idx = 11);
		map.put("tripmember", userSvc.planDetail(plan_idx)); 
		
		//3.places 조회
		map.put("places", placeSvc.selectByPlanIdx(plan_idx));
		return new ResponseEntity<HashMap<String, Object>>(map, HttpStatus.OK);
	}
	
	@PostMapping("/comment/insert")
	public ResponseEntity<?> insertComment(@RequestBody PlanCommentDto comment) throws SQLException {
		return new ResponseEntity<Integer>(pcSvc.insertPlanComment(comment), HttpStatus.OK);
	}
	
	@GetMapping("/comment/{plan_idx}")
	public ResponseEntity<?> selectByPlanId(@PathVariable int plan_idx) throws SQLException{
		return new ResponseEntity<List<PlanCommentDto>>(pcSvc.selectByPlanIdx(plan_idx), HttpStatus.OK);
	}
	
	@PostMapping("/leave")
	public ResponseEntity<?> leavePlan(@RequestBody TripMemberDto tripMember) throws SQLException {
		System.out.println(tripMember);
		try {
			tmSvc.leavePlan(tripMember);
			if (tmSvc.countPlanUser(tripMember.getPlan_idx()) == 0) {
				planSvc.deletePlan(tripMember.getPlan_idx());
			}
			return new ResponseEntity<Integer>(1, HttpStatus.OK);
		}
		catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
		}
	}
}
