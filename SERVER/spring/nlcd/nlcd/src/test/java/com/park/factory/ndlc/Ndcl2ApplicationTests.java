package com.park.factory.ndlc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.park.factory.ndlc.db.entitys.versions;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Transactional
@SpringBootTest
class Ndcl2ApplicationTests {
	@Autowired
	versionservice mService;
	@Test
	void contextLoads() {
	}
	private static final Logger log = LoggerFactory.getLogger(scheduleTest.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
		log.info("The time is now {}", dateFormat.format(new Date()));
	}
	@Test
	public void Addversion() {
		
		long time = System.currentTimeMillis();
		mService.insertResult(new versions(new Date(time) , false , dateFormat.format(new Date())));
		log.info("-------------------------------"+mService.getVersion().toString());
		
	}
}
