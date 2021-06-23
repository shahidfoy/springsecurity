package com.shahidfoy.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@ComponentScans({ @ComponentScan("com.shahidfoy.springsecurity.controller"), @ComponentScan("com.shahid.foy.springsecurity.config") })
@EnableJpaRepositories("com.shahidfoy.springsecurity.repository")
@EntityScan("com.shahidfoy.springsecurity.model")
@EnableWebSecurity(debug = true)
public class SpringsecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityApplication.class, args);
	}

}
