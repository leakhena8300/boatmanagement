package com.boat.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class BoatManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoatManagementApplication.class, args);
	}

}
