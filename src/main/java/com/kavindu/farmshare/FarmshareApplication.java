package com.kavindu.farmshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FarmshareApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmshareApplication.class, args);
	}

}
