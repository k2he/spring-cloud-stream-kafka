package com.demo.springcloudstreamkafka.config;

import org.springframework.cloud.stream.annotation.EnableBinding;
import com.demo.springcloudstreamkafka.stream.GreetingsStreams;

@EnableBinding(GreetingsStreams.class)
public class StreamsConfig {

}
