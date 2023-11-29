package com.maybank.demo.DemoRestControllerSimulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class DemoRestControllerSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoRestControllerSimulatorApplication.class, args);
	}

	@Bean
	public WebClient getWebClient(){
		return WebClient.create();
	}

}
