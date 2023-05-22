package com.ssafy.model.repository;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.dto.PlaceDto;

@Mapper
public interface PlaceRepository {
	int insertPlace(PlaceDto place) throws SQLException;
}
