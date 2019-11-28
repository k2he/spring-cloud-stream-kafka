package com.springcloudstreamkafka.producerooc.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import com.springcloudstreamkafka.producerooc.stream.ApplicationProcessStreams;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@EnableBinding(ApplicationProcessStreams.class)
@EnableKafkaStreams
@Configuration
public class StreamsConfig {

}
