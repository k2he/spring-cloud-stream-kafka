package com.springcloudstreamkafka.publisherooc.service;

import javax.validation.constraints.NotNull;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.kafkastream.demo.lib.model.ProcessEvent;
import com.kafkastream.demo.lib.model.ProcessResult;
import com.kafkastream.demo.lib.model.ProcessStatus;
import com.kafkastream.demo.lib.model.ServiceName;
import com.springcloudstreamkafka.publisherooc.model.ApplicationStatus;
import com.springcloudstreamkafka.publisherooc.stream.ApplicationProcessStreams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessResultListener {

  @NotNull
  private final ApplicationService applicationService;

  @StreamListener(ApplicationProcessStreams.PROCESS_RESULT_INPUT)
  public void handleProcessFinished(@Payload ProcessResult result) {
    applicationService.logProcessResult(result);

    ApplicationStatus appStatus = applicationService.updateAppStatus(result);

    log.info("(" + result.getApplicationNumber() + ") Burea Status: " + appStatus.getBureaStatus() + " Ajudication Status: "
        + appStatus.getAjdcStatus());
    if (appStatus.getBureaStatus().equals(ProcessStatus.COMPLETED)
        && appStatus.getAjdcStatus().equals(ProcessStatus.COMPLETED)) {
      applicationService.addTolog(result.getApplicationNumber(),
          "Application (" + result.getApplicationNumber() + ") Process Done at " + ApplicationService.getCurrentTimeString());

    }
  }
}
