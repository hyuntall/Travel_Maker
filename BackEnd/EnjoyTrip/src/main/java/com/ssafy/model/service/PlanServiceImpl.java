package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dto.PlanDto;
import com.ssafy.model.repository.PlanRepository;

@Service
public class PlanServiceImpl implements PlanService{

	@Autowired
	PlanRepository planRepo;
	
	@Override
	public int makePlan(PlanDto plan) throws SQLException {
		return planRepo.makePlan(plan);
	}

	@Override
	public int updatePlan(PlanDto plan) throws SQLException {
		return planRepo.updatePlan(plan);
	}


	@Override
	public PlanDto selectByIdx(int idx) throws SQLException {
		return planRepo.selectByIdx(idx);
	}

	@Override
	public int getCurrentIdx() throws SQLException {
		return planRepo.getCurrentIdx();
	}
}
