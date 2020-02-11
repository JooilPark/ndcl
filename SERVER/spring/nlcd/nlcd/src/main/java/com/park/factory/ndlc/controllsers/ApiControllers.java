package com.park.factory.ndlc.controllsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.park.factory.ndlc.db.DAOversions;

@RestController
public class ApiControllers {
	
	@GetMapping(path = "/HelloWorld")
	public String HelloWorld() {
		return  LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}
	@Autowired
	private DAOversions mDAOversions;
	@GetMapping(path = "/getVersion")
	public String helloWorld() {
		return String.format("%s %s", mDAOversions.selectName(), LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
	}
	@GetMapping(path = "/getfileUrl")
	public String getUrl() {
		return "https://www.data.go.kr/comm/file/download.do?atchFileId=FILE_000000001598699&fileDetailSn=0";
	}
}

