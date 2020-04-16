package com.park.factory.ndlc.db.entitys;

import lombok.Data;

@Data
public class citys {
	public int id;
	public String cityname;
	public int cityid = 0;
	public int parentid = 0;

	public citys(String city, int cityid) {
		this.cityname = city;
		this.cityid = cityid;
	}

	public citys(String city, int cityid, int parentid) {
		this.cityname = city;
		this.cityid = cityid;
		this.parentid = parentid;
	}
}
