package com.ssafy.model.repository;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.PlanCommentDto;

@Mapper
public interface PlanCommentRepository {
	public int insertPlanComment(PlanCommentDto comment) throws SQLException;
	public int deletePlanComment(int idx) throws SQLException;
	public List<PlanCommentDto> selectByPlanIdx(int plan_idx) throws SQLException;
}
