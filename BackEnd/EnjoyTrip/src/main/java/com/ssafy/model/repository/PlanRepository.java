package com.ssafy.model.repository;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.PlanDto;

@Mapper
public interface PlanRepository {
	public int makePlan(PlanDto plan) throws SQLException;
	public int updatePlan(PlanDto plan) throws SQLException;
	public int getCurrentIdx() throws SQLException;
	public List<PlanDto> selectByUser(String user_id) throws SQLException;
}
