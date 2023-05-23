package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dto.PlaceDto;
import com.ssafy.model.repository.PlaceRepository;

@Service
public class PlaceServiceImpl implements PlaceService{

	@Autowired
	PlaceRepository placeRepo;
	
	@Override
	public int insertPlace(PlaceDto place) throws SQLException {
		// TODO Auto-generated method stub
		return placeRepo.insertPlace(place);
	}

	@Override
	public List<PlaceDto> selectByPlanIdx(int plan_idx) throws SQLException {
		// TODO Auto-generated method stub
		return placeRepo.selectByPlanIdx(plan_idx);
	}

}
