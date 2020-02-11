package com.park.factory.ndlc.db.entitys;



import java.util.Date;

import lombok.Data;

@Data
public class versions {
	private int id;
	private Date updatetime;
	private boolean result;
	private String filename;
	public versions() {
		
	}
	public boolean isResult() {
		return result;
	}
	public String getfilename() {
		return filename;
	}
	public versions(Date updatetime , boolean result , String filename) {
		this.filename = filename;
		this.result = result;
		this.updatetime = updatetime;
	} 
	
	@Override
	public String toString() {
	
		return filename;
	}
}
