package com.pruebacc.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
 *Class that initialize the Spring data redis application and starts the server in order to listen to the
 *different API calls
 */
@SpringBootApplication
public class SpringDataRedisPruebaccApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringDataRedisPruebaccApplication.class, args);
	}
}
