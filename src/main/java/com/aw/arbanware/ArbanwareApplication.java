package com.aw.arbanware;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableJpaAuditing
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class ArbanwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArbanwareApplication.class, args);
	}

}
