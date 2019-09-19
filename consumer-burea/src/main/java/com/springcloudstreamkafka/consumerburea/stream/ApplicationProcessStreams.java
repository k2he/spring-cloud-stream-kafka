package com.springcloudstreamkafka.consumerburea.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ApplicationProcessStreams {
  public static final String BUREAU_INPUT = "bureau-in";

  public static final String PROCESS_RESULT_OUTPUT = "process-result-out";
  
  @Input(BUREAU_INPUT)
  SubscribableChannel inboundBureau();

  @Output(PROCESS_RESULT_OUTPUT)
  MessageChannel outboundProcessResult();
}
