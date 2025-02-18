package com.kavindu.farmshare;

import com.kavindu.farmshare.repo.FarmRepo;
import com.kavindu.farmshare.repo.PhotosRepo;
import com.kavindu.farmshare.repo.StockPriceRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
