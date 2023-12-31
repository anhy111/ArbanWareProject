package com.aw.arbanware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableJpaAuditing
@SpringBootApplication
public class ArbanwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArbanwareApplication.class, args);
	}

}
