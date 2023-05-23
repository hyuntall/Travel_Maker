package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.dto.PlaceDto;

public interface PlaceService {
	int insertPlace(PlaceDto place) throws SQLException;
	List<PlaceDto> selectByPlanIdx(int plan_idx) throws SQLException;
}
