package com.Gururaj.eureka_service_resgistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServiceResgistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServiceResgistryApplication.class, args);
	}
}
