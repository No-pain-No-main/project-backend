package com.adanext.NoPainNoMain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NoPainNoMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoPainNoMainApplication.class, args);
	}

}
