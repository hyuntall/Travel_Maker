package com.ssafy.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ssafy.dto.PlaceDto;
import com.ssafy.dto.PlanDto;
import com.ssafy.model.service.PlaceService;
import com.ssafy.model.service.PlanService;

@Controller
@RequestMapping("/plan")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
public class PlanController {
	@Autowired
	PlaceService placeSvc;
	
	@Autowired
	PlanService planSvc;
	
	@PostMapping("/insert")
	public ResponseEntity<?> insertUser(@RequestBody PlanDto plan) throws SQLException {
		int r = planSvc.makePlan(plan);
		if (r == 1 && plan.getPlaces() != null) {
			for (PlaceDto place : plan.getPlaces()) {
				System.out.println(place);
//				placeSvc.insertPlace(place);
			}
			return new ResponseEntity<Integer>(1, HttpStatus.OK);
		}
		return new ResponseEntity<Integer>(0, HttpStatus.BAD_REQUEST);
	}
}
