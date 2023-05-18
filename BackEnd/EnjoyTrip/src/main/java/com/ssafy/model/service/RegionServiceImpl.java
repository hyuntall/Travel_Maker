package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dto.RegionDto;
import com.ssafy.model.repository.RegionRepository;

@Service
public class RegionServiceImpl implements RegionService{
	
	@Autowired
	private RegionRepository regionRepository;

	@Override
	public List<RegionDto> selectAll() throws SQLException {
		return regionRepository.selectAll();
	}

	@Override
	public List<RegionDto> selectAByKeyword(String keyword) throws SQLException {
		return regionRepository.selectAByKeyword(keyword);
	}

	@Override
	public List<RegionDto> selectABycate1(String cate1) throws SQLException {
		return regionRepository.selectABycate1(cate1);
	}
}
