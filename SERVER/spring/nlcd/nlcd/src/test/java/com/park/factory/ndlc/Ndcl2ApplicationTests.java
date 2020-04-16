package com.park.factory.ndlc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.park.factory.ndlc.db.entitys.courses;
import com.park.factory.ndlc.db.entitys.versions;
import com.park.factory.ndlc.db.mappers.cityMapper;
import com.park.factory.ndlc.db.mappers.coursesMapper;
import com.park.factory.ndlc.db.mappers.versionMapper;
import com.park.factory.ndlc.schrdule.excel.excelUtils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@SpringBootTest
class Ndcl2ApplicationTests {
	@Autowired
	private versionMapper mVMapper;
	@Autowired
	private coursesMapper mCMapper;
	@Autowired
	private cityMapper mCityMapper;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	excelUtils mExcelUtils = new excelUtils();
	
	@Test
	void contextLoads() {
	}
	private static final Logger log = LoggerFactory.getLogger(scheduleTest.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Test
	public void startFirst() {
		fileDownLoader();
		
		try {
			
				xlsToDB();
				for(courses c : mExcelUtils.courses) {
					mCMapper.insert(c);
				}
				
				log.info("-------------------------------------------" );
				Set<String> kesy = mExcelUtils.mCitys.keySet();
				for(String k: kesy) {
					mCityMapper.insert(mExcelUtils.mCitys.get(k));
				}
				
				kesy = mExcelUtils.mCitySubs.keySet();
				for(String k: kesy) {
					mCityMapper.insert(mExcelUtils.mCitySubs.get(k));
				}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("-------------------------------------------" + e.getMessage() );
		}
	}
	private boolean fileDownLoader() {
		Date now = new Date();
		String strDate = sdf.format(now);
		ClassPathResource resource = new ClassPathResource("application.properties");
		File xlsfile = null;
		try {
			log.info("downstart");
			xlsfile = new File(resource.getFile().getParentFile(), "/files/datalist" + strDate + ".xls");
			if(!xlsfile.exists()) {
				URL url = new URL("https://www.data.go.kr/comm/file/download.do?atchFileId=FILE_000000001598699&fileDetailSn=0");
				ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
				FileOutputStream fileOutputStream = new FileOutputStream(xlsfile);
				FileChannel channel = fileOutputStream.getChannel();
				fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
				mVMapper.insertResult(new versions(now, true, xlsfile.getName()));
				log.info("downend / " + mVMapper.selectversions().getfilename());
			}else {
				mVMapper.insertResult(new versions(now, true, xlsfile.getName()));
				log.info("HaveFile / " + mVMapper.selectversions().getfilename());
			}
			
			
			return true;
		} catch (MalformedURLException e) {
			mVMapper.insertResult(new versions(now, false, xlsfile.getName() + "[MalformedURLException]"));

			e.printStackTrace();
		} catch (IOException e) {

			mVMapper.insertResult(new versions(now, false, xlsfile.getName() + "[IOException]"));
			e.printStackTrace();
		}
		return false;
		
	}
	@Async
	private void xlsToDB() throws IOException {
		log.info("xlsToDB / " + mVMapper.selectversions().isResult());
		if(mVMapper.selectversions().isResult()) {
			ClassPathResource resource = new ClassPathResource("application.properties");
			File lsfile = new File(resource.getFile().getParentFile(), "/files/" + mVMapper.selectversions().getfilename()); 
			log.info("lsfile[" + lsfile.exists() + "]" + lsfile.getPath() );
			mExcelUtils.CreateWorkBook("xls", lsfile);
			log.info("-------------------------------------------" );
		}
		
	}
}
