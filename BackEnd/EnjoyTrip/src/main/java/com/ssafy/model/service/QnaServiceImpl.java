package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dto.QnaDto;
import com.ssafy.model.repository.QnaRepository;

import lombok.RequiredArgsConstructor;

@Service
public class QnaServiceImpl implements QnaService{
	
	@Autowired
	private QnaRepository qnaRepository;

	@Override
	public List<QnaDto> selectAll() throws SQLException {
		// TODO Auto-generated method stub
		return qnaRepository.selectAll();
	}

	@Override
	public void insert(QnaDto dto) throws SQLException {
		// TODO Auto-generated method stub
		qnaRepository.insert(dto);
	}

	@Override
	public void update(QnaDto dto) throws SQLException {
		// TODO Auto-generated method stub
		qnaRepository.update(dto);
	}



	@Override
	public void delete(int id) throws SQLException {
		// TODO Auto-generated method stub
		qnaRepository.delete(id);
	}

	@Override
	public QnaDto selectById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return qnaRepository.selectById(id);
	}
	
}
