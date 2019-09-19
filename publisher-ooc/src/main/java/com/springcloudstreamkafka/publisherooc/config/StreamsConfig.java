package com.springcloudstreamkafka.publisherooc.config;

import org.springframework.cloud.stream.annotation.EnableBinding;

import com.springcloudstreamkafka.publisherooc.stream.ApplicationProcessStreams;

@EnableBinding(ApplicationProcessStreams.class)
public class StreamsConfig {

}
