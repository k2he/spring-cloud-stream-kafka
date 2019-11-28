package com.springcloudstreamkafka.consumertsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.springcloudstreamkafka.consumertsys.stream.ApplicationProcessStreams;


@SpringBootApplication
@EnableBinding(ApplicationProcessStreams.class)
public class ConsumerTsysApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerTsysApplication.class, args);
	}

}
