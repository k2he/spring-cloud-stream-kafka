package com.springcloudstreamkafka.publisherooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import com.springcloudstreamkafka.publisherooc.stream.ApplicationProcessStreams;

@SpringBootApplication
@EnableBinding(ApplicationProcessStreams.class)
public class PublisherOocApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublisherOocApplication.class, args);
	}

}
