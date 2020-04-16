package com.park.factory.ndlc.db.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.park.factory.ndlc.db.entitys.citys;



@Mapper
public interface cityMapper {
	void insert(citys c);
	void allclear();
	List<citys> getAllCitys();

}
