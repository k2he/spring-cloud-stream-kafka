package com.springcloudstreamkafka.consumertsys.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ApplicationProcessStreams {

  public static final String TSYS_INPUT = "tsys-in";

  public static final String PROCESS_RESULT_OUTPUT = "process-result-out";
   
  @Input(TSYS_INPUT)
  SubscribableChannel inboundTsys();

  @Output(PROCESS_RESULT_OUTPUT)
  MessageChannel outboundProcessResult();
}
