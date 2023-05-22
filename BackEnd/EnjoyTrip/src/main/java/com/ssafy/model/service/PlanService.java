package com.ssafy.model.service;

import java.sql.SQLException;

import com.ssafy.dto.PlanDto;
public interface PlanService {
	public int makePlan(PlanDto plan) throws SQLException;
	public int updatePlan(PlanDto plan) throws SQLException;
	public int getPlanIdx(String user_id) throws SQLException;
}
