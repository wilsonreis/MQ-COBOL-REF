package com.santander.kpv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJms
public class MQCobolRef {

	public static void main(String[] args) {

		SpringApplication.run(MQCobolRef.class, args);
	}
}
