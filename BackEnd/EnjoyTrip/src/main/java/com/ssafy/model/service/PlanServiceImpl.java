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
	public int getPlanIdx(String user_id) throws SQLException {
		return planRepo.getPlanIdx(user_id);
	}

	@Override
	public List<PlanDto> selectByUser(String user_id) throws SQLException {
		return planRepo.selectByUser(user_id);
	}
}
