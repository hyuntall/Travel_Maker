package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.PlanCommentDto;

public interface PlanCommentService {
	public int insertPlanComment(PlanCommentDto comment) throws SQLException;
	public int deletePlanComment(int idx) throws SQLException;
	public List<PlanCommentDto> selectByPlanIdx(int plan_idx) throws SQLException;
}
