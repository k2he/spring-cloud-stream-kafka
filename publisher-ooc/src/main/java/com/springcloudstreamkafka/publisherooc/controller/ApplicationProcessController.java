package com.springcloudstreamkafka.publisherooc.controller;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.springcloudstreamkafka.publisherooc.model.ApplicationLog;
import com.springcloudstreamkafka.publisherooc.model.ApplicationProcess;
import com.springcloudstreamkafka.publisherooc.repository.ApplicationLogRepository;
import com.springcloudstreamkafka.publisherooc.repository.ApplicationProcessRepository;
import com.springcloudstreamkafka.publisherooc.service.ApplicationProcessService;
import com.springcloudstreamkafka.publisherooc.service.ApplicationService;
import com.kafkastream.demo.lib.model.ProcessEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApplicationProcessController {

  @NonNull
  private final ApplicationProcessService applicationProcessService;

  @NotNull
  private final ApplicationService applicationService;

  @NotNull
  private final ApplicationProcessRepository applicationProcessRespository;

  @NotNull
  private final ApplicationLogRepository applicationLogRespository;

  @PostMapping("/start")
  public ResponseEntity<String> startProcess(@RequestBody ProcessEvent event) {
    log.info("============ Start Processing Single Applicaions at " + ApplicationService.getCurrentTimeString()
        + "================");

    // Set initial Status for all services.
    applicationService.initProcess(event.getApplicationNumber());

    event.setTime(LocalDateTime.now());
    applicationProcessService.startApplicationProcessing(event);
    return new ResponseEntity<>("Kafka Stream Message send successfully!", HttpStatus.OK);
  }

  @RequestMapping(value = "/start/{numOfApplication}", method = RequestMethod.GET)
  public ResponseEntity<String> getActionByApplicationNumber(
      @PathVariable Integer numOfApplication) {

    log.info("=================== Start Processing Bulk of Applicaions at "
        + ApplicationService.getCurrentTimeString() + "============");

    for (int i = 1; i <= numOfApplication; i++) {
      String applicationNumber = "F" + i;
      ProcessEvent event = ProcessEvent.builder().applicationNumber(applicationNumber)
          .time(LocalDateTime.now()).build();
      applicationProcessService.startApplicationProcessing(event);
    }

    return new ResponseEntity<>("Kafka Stream Message Bulk run finished!", HttpStatus.OK);
  }

  @RequestMapping(value = "/bulkstart", method = RequestMethod.GET)
  public ResponseEntity<String> getActionByApplicationNumber(
      @RequestParam("startNum") Integer startNum, @RequestParam("endNum") Integer endNum) {
    
    log.info("==================================== Start Processing Bulk of Applicaions at "
        + ApplicationService.getCurrentTimeString() + "===========================");

    for (int i = startNum; i <= endNum; i++) {
      String applicationNumber = "F" + i;
      ProcessEvent event = ProcessEvent.builder().applicationNumber(applicationNumber)
          .time(LocalDateTime.now()).build();
      applicationProcessService.startApplicationProcessing(event);
    }

    return new ResponseEntity<>("Kafka Stream Message Bulk run finished!", HttpStatus.OK);
  }

  @RequestMapping(value = "actions/{applicationNumber}", method = RequestMethod.GET)
  public List<ApplicationProcess> getActionByApplicationNumber(
      @PathVariable String applicationNumber) {
    return applicationProcessRespository.findByApplicationNumber(applicationNumber);
  }

  @RequestMapping(value = "actions", method = RequestMethod.GET)
  public List<ApplicationProcess> getAllActions() {
    return applicationProcessRespository.findAll();
  }

  @RequestMapping(value = "logs/{applicationNumber}", method = RequestMethod.GET)
  public List<ApplicationLog> getLogsByApplicationNumber(@PathVariable String applicationNumber) {
    return applicationLogRespository.findByApplicationNumber(applicationNumber);
  }

  @RequestMapping(value = "logs", method = RequestMethod.GET)
  public List<ApplicationLog> getAllLogs() {
    return applicationLogRespository.findAll();
  }
}
