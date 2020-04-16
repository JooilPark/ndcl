package com.park.factory.ndlc.schrdule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tomcat.jni.Mmap;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.park.factory.ndlc.db.entitys.courses;
import com.park.factory.ndlc.db.entitys.versions;
import com.park.factory.ndlc.db.mappers.cityMapper;
import com.park.factory.ndlc.db.mappers.coursesMapper;
import com.park.factory.ndlc.db.mappers.versionMapper;
import com.park.factory.ndlc.schrdule.excel.excelUtils;

@Component
public class autoFileDownLoad {
	private static final Logger log = LoggerFactory.getLogger(autoFileDownLoad.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	excelUtils mExcelUtils = new excelUtils();
	@Autowired
	private versionMapper mVMapper;
	@Autowired
	private coursesMapper mCMapper;
	@Autowired
	private cityMapper mCityMapper;
	private boolean isStart = false;
	@Scheduled(cron = " 0 0 0 1 1/1 ?")
	public void startDownLoad() {

		if(fileDownLoader()) {
			try {
				xlsToDB();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};

	}
	//@Scheduled(fixedRate = 3000000)
	public void checkData() {
		LocalDate ld = mVMapper.selectversions().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate nowld =  new Date(System.currentTimeMillis()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		log.info("재부팅시 날짜 확인 [" + ld.getMonthValue() + "][" + nowld.getMonthValue() );
		if(ld.getMonthValue() != nowld.getMonthValue()) {
			startFirst();
		}
	}
	//@Scheduled(cron = " 0/30 * * * * *")
	//@Scheduled(fixedRate = 3000000)
	public void startFirst() {
		fileDownLoader();
		
		try {
			if(!isStart) {
				xlsToDB();
				mCityMapper.allclear();
				mCMapper.allclear();
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
				isStart = false;
				log.info("-------------------------------------------END" );
			}else {
				log.info("작업중" );
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	/*
	 * @Scheduled(fixedRate = 5000) public void startDownLoad2() { SimpleDateFormat
	 * sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); Date now = new Date();
	 * String strDate = sdf.format(now); log.info("Java cron job expression 2:: " +
	 * strDate);
	 * 
	 * }
	 */
}
