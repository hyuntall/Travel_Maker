package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dto.TripMemberDto;
import com.ssafy.model.repository.TripMemberRepository;

@Service
public class TripMemberServiceImpl implements TripMemberService {

	@Autowired
	TripMemberRepository tmRepo;
	@Override
	public List<Integer> selectPlanIdx(String user_id) throws SQLException {
		return tmRepo.selectPlanIdx(user_id);
	}

	@Override
	public int insertUserOfPlan(TripMemberDto tripMember) throws SQLException {
		return tmRepo.insertUserOfPlan(tripMember);
	}

	@Override
	public int leavePlan(TripMemberDto tripMember) throws SQLException {
		return tmRepo.leavePlan(tripMember);
	}

	@Override
	public int countPlanUser(int idx) throws SQLException {
		return tmRepo.countPlanUser(idx);
	}
}
