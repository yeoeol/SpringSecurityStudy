package com.example.springjwt;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJwtApplication {

	public static void main(String[] args) {
		System.out.println(new Date());
		SpringApplication.run(SpringJwtApplication.class, args);
	}

}
