package com.demo.springcloudstreamkafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringCloudStreamKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamKafkaApplication.class, args);
	}

}
