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
import java.util.Date;

import org.apache.tomcat.jni.Mmap;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.park.factory.ndlc.db.entitys.versions;
import com.park.factory.ndlc.db.mappers.versionMapper;

@Component
public class autoFileDownLoad {
	private static final Logger log = LoggerFactory.getLogger(autoFileDownLoad.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	@Autowired
	private versionMapper mVMapper;

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

	@Scheduled(cron = " 0/30 * * * * *")
	public void startFirst() {
		//fileDownLoader();
		
		try {
			xlsToDB();
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
			URL url = new URL("https://www.data.go.kr/comm/file/download.do?atchFileId=FILE_000000001598699&fileDetailSn=0");
			ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());
			FileOutputStream fileOutputStream = new FileOutputStream(xlsfile);
			FileChannel channel = fileOutputStream.getChannel();
			fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
			mVMapper.insertResult(new versions(now, true, xlsfile.getName()));
			log.info("downend" + mVMapper.selectversions().getfilename());
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
		
		if(mVMapper.selectversions().isResult()) {
			ClassPathResource resource = new ClassPathResource("application.properties");
			File lsfile = new File(resource.getFile().getParentFile(), "/files/" + mVMapper.selectversions().getfilename()); 
			log.info("lsfile[" + lsfile.exists() + "]" + lsfile.getPath() );
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
