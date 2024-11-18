package com.example.cellphonesclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class CellphonescloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(CellphonescloneApplication.class, args);
	}

}
