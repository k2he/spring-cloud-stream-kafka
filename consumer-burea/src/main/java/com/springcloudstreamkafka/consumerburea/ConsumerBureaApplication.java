package com.springcloudstreamkafka.consumerburea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import com.springcloudstreamkafka.consumerburea.stream.ApplicationProcessStreams;

@SpringBootApplication
@EnableBinding(ApplicationProcessStreams.class)
public class ConsumerBureaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerBureaApplication.class, args);
	}

}
