package com.demo.springcloudstreamkafka.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ApplicationProcessStreams {
  public static final String BUREAU_INPUT = "bureau-in";
  public static final String BUREAU_OUTPUT = "bureau-out";
  
  public static final String AJDC_INPUT = "ajdc-in";
  public static final String AJDC_OUTPUT = "ajdc-out";
  
  public static final String PROCESS_RESULT_INPUT = "process-result-in";
  public static final String PROCESS_RESULT_OUTPUT = "process-result-out";
  
  @Input(BUREAU_INPUT)
  SubscribableChannel inboundBureau();

  @Output(BUREAU_OUTPUT)
  MessageChannel outboundBureau();
  
  @Input(AJDC_INPUT)
  SubscribableChannel inboundAjdc();

  @Output(AJDC_OUTPUT)
  MessageChannel outboundAjdc();
  
  @Input(PROCESS_RESULT_INPUT)
  SubscribableChannel inboundProcessResult();

  @Output(PROCESS_RESULT_OUTPUT)
  MessageChannel outboundProcessResult();
}
