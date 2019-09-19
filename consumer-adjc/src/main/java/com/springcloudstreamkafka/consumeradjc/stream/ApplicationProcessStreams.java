package com.springcloudstreamkafka.consumeradjc.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ApplicationProcessStreams {

  public static final String AJDC_INPUT = "ajdc-in";

  public static final String PROCESS_RESULT_OUTPUT = "process-result-out";
   
  @Input(AJDC_INPUT)
  SubscribableChannel inboundAjdc();

  @Output(PROCESS_RESULT_OUTPUT)
  MessageChannel outboundProcessResult();
}
