package com.park.factory.ndlc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.park.factory.ndlc.db.entitys.versions;
import com.park.factory.ndlc.db.mappers.versionMapper;

@Service
@Transactional
public class versionservice {
	@Autowired
	versionMapper mVersionMapper;
	
	public versions getVersion() {
		return mVersionMapper.selectversions();
	}
	public void insertResult(versions v ) {
		mVersionMapper.insertResult(v);
	
	}
	
}	
