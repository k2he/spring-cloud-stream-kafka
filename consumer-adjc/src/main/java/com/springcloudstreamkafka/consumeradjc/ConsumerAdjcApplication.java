package com.springcloudstreamkafka.consumeradjc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import com.springcloudstreamkafka.consumeradjc.stream.ApplicationProcessStreams;

@SpringBootApplication
@EnableBinding(ApplicationProcessStreams.class)
public class ConsumerAdjcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerAdjcApplication.class, args);
	}

}
