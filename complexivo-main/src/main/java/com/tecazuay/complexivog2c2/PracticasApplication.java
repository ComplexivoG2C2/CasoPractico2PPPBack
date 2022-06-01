package com.tecazuay.complexivog2c2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;



@SpringBootApplication(exclude = { ThymeleafAutoConfiguration.class })
public class PracticasApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticasApplication.class, args);
	}

}
