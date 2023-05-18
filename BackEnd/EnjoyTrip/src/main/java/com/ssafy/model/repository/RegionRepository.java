package com.ssafy.model.repository;

import java.sql.SQLException;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ssafy.dto.QnaDto;
import com.ssafy.dto.RegionDto;
import com.ssafy.dto.UserDto;



@Mapper
public interface RegionRepository {
	public List<RegionDto> selectAll() throws SQLException;
	public List<RegionDto> selectAByKeyword(String keyword) throws SQLException;
	public List<RegionDto> selectABycate1(String cate1) throws SQLException;
	
}
