package com.kdatalab.bridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.kdatalab")
public class DatabridgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabridgeApplication.class, args);
	}

}
