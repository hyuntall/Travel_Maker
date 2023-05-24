package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.dto.PlanCommentDto;
import com.ssafy.model.repository.PlanCommentRepository;

public class PlanCommentServiceImpl implements PlanCommentService{

	@Autowired
	PlanCommentRepository pcRepo;
	
	@Override
	public int insertPlanComment(PlanCommentDto comment) throws SQLException {
		return pcRepo.insertPlanComment(comment);
	}

	@Override
	public int deletePlanComment(int idx) throws SQLException {
		return pcRepo.deletePlanComment(idx);
	}

	@Override
	public List<PlanCommentDto> selectByPlanIdx(int plan_idx) throws SQLException {
		return pcRepo.selectByPlanIdx(plan_idx);
	}
}
