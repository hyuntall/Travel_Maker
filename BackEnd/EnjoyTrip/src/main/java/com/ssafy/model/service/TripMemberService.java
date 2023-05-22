package com.ssafy.model.service;

import java.sql.SQLException;

import com.ssafy.dto.TripMemberDto;

public interface TripMemberService {
	public int selectPlanIdx(String user_id) throws SQLException;
	public int insertUserOfPlan(TripMemberDto tripMember) throws SQLException;
}
