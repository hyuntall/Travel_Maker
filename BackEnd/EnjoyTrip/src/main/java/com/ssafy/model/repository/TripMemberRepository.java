package com.ssafy.model.repository;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.TripMemberDto;

@Mapper
public interface TripMemberRepository {
	public List<Integer> selectPlanIdx(String user_id) throws SQLException;
	public int insertUserOfPlan(TripMemberDto tripMember) throws SQLException;
}
