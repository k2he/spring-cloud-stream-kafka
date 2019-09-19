package com.springcloudstreamkafka.publisherooc.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ApplicationProcessStreams {
  public static final String BUREAU_OUTPUT = "bureau-out";
  
  public static final String AJDC_OUTPUT = "ajdc-out";
  
  public static final String PROCESS_RESULT_INPUT = "process-result-in";
  
  @Output(BUREAU_OUTPUT)
  MessageChannel outboundBureau();

  @Output(AJDC_OUTPUT)
  MessageChannel outboundAjdc();
  
  @Input(PROCESS_RESULT_INPUT)
  SubscribableChannel inboundProcessResult();
}
