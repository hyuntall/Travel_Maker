package com.ssafy.model.repository;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.PlanDto;

@Mapper
public interface PlanRepository {
	public int makePlan(PlanDto plan) throws SQLException;
	public int updatePlan(PlanDto plan) throws SQLException;
	public int getPlanIdx(String user_id) throws SQLException;
}
