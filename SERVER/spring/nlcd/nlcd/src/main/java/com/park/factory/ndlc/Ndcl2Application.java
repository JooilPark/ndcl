package com.park.factory.ndlc;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class Ndcl2Application {
	static public Logger logger = org.slf4j.LoggerFactory.getLogger(Ndcl2Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Ndcl2Application.class, args);
	}
}
