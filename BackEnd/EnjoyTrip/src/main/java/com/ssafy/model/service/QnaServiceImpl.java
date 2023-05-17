package com.ssafy.model.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dto.QnaDto;
import com.ssafy.model.repository.QnaRepository;

import lombok.RequiredArgsConstructor;

@Service
public class QnaServiceImpl implements QnaService{
	
	@Autowired
	private SqlSession sqlsession;

	@Override
	public List<QnaDto> selectAll() throws SQLException {
		// TODO Auto-generated method stub
		return sqlsession.getMapper(QnaRepository.class).selectAll();
				
	}

	@Override
	public void insert(QnaDto dto) throws SQLException {
		// TODO Auto-generated method stub
		sqlsession.getMapper(QnaRepository.class).insert(dto);
	}

	@Override
	public void update(QnaDto dto) throws SQLException {
		// TODO Auto-generated method stub
		sqlsession.getMapper(QnaRepository.class).update(dto);
		
	}



	@Override
	public void delete(int id) throws SQLException {
		// TODO Auto-generated method stub
		sqlsession.getMapper(QnaRepository.class).delete(id);
		
	}

	@Override
	public QnaDto selectById(int id) throws SQLException {
		// TODO Auto-generated method stub
		return sqlsession.getMapper(QnaRepository.class).selectById(id);
	}
	
}
