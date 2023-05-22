package com.ssafy.model.service;

import java.sql.SQLException;

import com.ssafy.dto.PlaceDto;

public interface PlaceService {
	int insertPlace(PlaceDto place) throws SQLException;
}
