package com.demo.springcloudstreamkafka.config;

import org.springframework.cloud.stream.annotation.EnableBinding;

import com.demo.springcloudstreamkafka.stream.ApplicationProcessStreams;

@EnableBinding(ApplicationProcessStreams.class)
public class StreamsConfig {

}
