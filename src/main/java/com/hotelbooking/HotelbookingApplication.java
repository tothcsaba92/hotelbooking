package com.hotelbooking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author csaba
 * @version 1.0
 * @since 20-07-2020
 *
 */

@SpringBootApplication
public class HotelbookingApplication {

	private static Logger logger = LoggerFactory.getLogger(HotelbookingApplication.class);
	
	public static void main(String[] args) {
		logger.info("Starting the hotel booking application..");
		SpringApplication.run(HotelbookingApplication.class, args);
		logger.info("Application was started successfully!");
	}
}
