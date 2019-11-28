package com.springcloudstreamkafka.producerooc.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.apache.kafka.streams.kstream.KStream;
import com.kafkastream.demo.lib.model.ApplicationResultEvent;

public interface ApplicationProcessStreams {
  public static final String BUREAU_OUTPUT = "bureau-out";
  
  public static final String AJDC_OUTPUT = "ajdc-out";
  
  public static final String PROCESS_RESULT_INPUT = "process-result-in";
  
  // output to stream for Application success/failed (If either of Burea or Ajdc failed, application is failed, otherwise is sucess)
  public static final String APPLICATION_RESULT_OUT = "application-result-out";
  
  public static final String APPLICATION_RESULT_IN = "application-result-in";
  
  public static final String APP_RESULT_COUNT_MATERALIZED_VIEW = "application-result-count-mv"; // 
  
  @Output(BUREAU_OUTPUT)
  MessageChannel outboundBureau();

  @Output(AJDC_OUTPUT)
  MessageChannel outboundAjdc();
  
  @Input(PROCESS_RESULT_INPUT)
  SubscribableChannel inboundProcessResult();
  
  @Output(APPLICATION_RESULT_OUT)
  MessageChannel outboundApplicationResult();
  
  @Input(APPLICATION_RESULT_IN)
  KStream<?, ?> inboundApplicationResult();
}
