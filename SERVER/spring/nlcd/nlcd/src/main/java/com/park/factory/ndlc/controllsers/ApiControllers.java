package com.park.factory.ndlc.controllsers;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.park.factory.ndlc.db.DAOversions;
import com.park.factory.ndlc.db.entitys.citys;
import com.park.factory.ndlc.db.entitys.courses;
import com.park.factory.ndlc.db.entitys.versions;
import com.park.factory.ndlc.db.mappers.cityMapper;
import com.park.factory.ndlc.db.mappers.coursesMapper;
import com.park.factory.ndlc.db.mappers.versionMapper;
import com.park.factory.ndlc.schrdule.autoFileDownLoad;

@RestController
public class ApiControllers {
	
	private Logger log = LoggerFactory.getLogger(ApiControllers.class); 
	@Autowired
	autoFileDownLoad mAutofileDown;
	@Autowired
	cityMapper mCityMapper;
	@Autowired
	coursesMapper mcoursesMapper;
	@Autowired
	versionMapper mDAOversions;
	
	
	@GetMapping(path = "/HelloWorld")
	public String HelloWorld() {
		return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}


	@GetMapping(path = "/getVersion")
	public versions helloWorld() {
		return  mDAOversions.selectversions();
	}

	@GetMapping(path = "/getfileUrl")
	public String getUrl() {
		return "https://www.data.go.kr/comm/file/download.do?atchFileId=FILE_000000001598699&fileDetailSn=0";
	}

	@GetMapping(path = "/startdownload")
	public String StartDownload() {
		mAutofileDown.startFirst();
		return "end";
	}

	@GetMapping(path = "/getCitys", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<citys> getCitys() {
		return mCityMapper.getAllCitys();
	}

	@PostMapping(path = "/getList", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<courses> getList(@RequestBody PageingParam body) {
		
	
		if(body.Query != null) {
			log.info("getList = " + body.Query);
			return mcoursesMapper.getListfind(body.Query,body.page *20, 40);	
		}else {
			
			
			return mcoursesMapper.getList(body.page *20, 40);
		}
		
		
		
	}
	
	static class  PageingParam {
		public String getQuery() {
			return Query;
		}

		public void setQuery(String query) {
			Query = query;
		}

		public int getPage() {
			return page;
		}

		public void setPage(int page) {
			this.page = page;
		}

		public int page = 0;
		public String Query = null;
		
	}

}
