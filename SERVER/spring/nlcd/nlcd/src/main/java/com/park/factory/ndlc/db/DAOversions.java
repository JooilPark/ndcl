package com.park.factory.ndlc.db;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DAOversions {
	protected static final String NAMESPACE = "com.park.factory.ndlc.db.DAOversions.";
	@Autowired
	private SqlSession sqlSession;
	
	public String  selectName() {
		return sqlSession.selectOne(NAMESPACE + "selectversions");
	}
	public void insertResult() {
		
	}
}

