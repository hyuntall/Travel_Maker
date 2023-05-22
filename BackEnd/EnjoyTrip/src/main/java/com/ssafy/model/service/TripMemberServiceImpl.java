package com.ssafy.model.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dto.TripMemberDto;
import com.ssafy.model.repository.TripMemberRepository;

@Service
public class TripMemberServiceImpl implements TripMemberService {

	@Autowired
	TripMemberRepository tmRepo;
	@Override
	public int selectPlanIdx(String user_id) throws SQLException {
		return tmRepo.selectPlanIdx(user_id);
	}

	@Override
	public int insertUserOfPlan(TripMemberDto tripMember) throws SQLException {
		return tmRepo.insertUserOfPlan(tripMember);
	}
}
