package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.TripMemberDto;

public interface TripMemberService {
	public List<Integer> selectPlanIdx(String user_id) throws SQLException;
	public int insertUserOfPlan(TripMemberDto tripMember) throws SQLException;
}
