package com.park.factory.ndlc.db.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.park.factory.ndlc.db.entitys.versions;

@Mapper
public interface versionMapper {
	versions selectversions ();
	void insertResult(versions version);
}
