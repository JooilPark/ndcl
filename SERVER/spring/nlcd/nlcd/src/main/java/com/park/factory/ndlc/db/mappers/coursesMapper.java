package com.park.factory.ndlc.db.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.park.factory.ndlc.db.entitys.courses;;

@Mapper
public interface coursesMapper {

	void insert(courses s);
	void allclear();
	List<courses> getListfind(String query , int start , int size);
	List<courses> getList( int start , int size);
	List<courses> searchList(String query , int start , int size);
	
}
