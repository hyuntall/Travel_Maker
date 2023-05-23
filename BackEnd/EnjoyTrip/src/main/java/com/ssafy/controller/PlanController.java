package com.ssafy.controller;

import java.sql.SQLException;
import java.util.ArrayList;
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

import com.ssafy.dto.PlaceDto;
import com.ssafy.dto.PlanDto;
import com.ssafy.dto.TripMemberDto;
import com.ssafy.model.service.PlaceService;
import com.ssafy.model.service.PlanService;
import com.ssafy.model.service.TripMemberService;

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
}
