package com.ssafy.model.repository;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.TripMemberDto;

@Mapper
public interface TripMemberRepository {
	public int selectPlanIdx(String user_id) throws SQLException;
	public int insertUserOfPlan(TripMemberDto tripMember) throws SQLException;
}
