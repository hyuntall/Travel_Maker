package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.PlanDto;
public interface PlanService {
	public int makePlan(PlanDto plan) throws SQLException;
	public int updatePlan(PlanDto plan) throws SQLException;
	public int getCurrentIdx() throws SQLException;
	public List<PlanDto> selectByUser(String user_id) throws SQLException;
}
