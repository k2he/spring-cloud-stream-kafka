package com.springcloudstreamkafka.producerooc.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kafkastream.demo.lib.model.ProcessEvent;
import com.kafkastream.demo.lib.model.ProcessStatus;
import com.springcloudstreamkafka.producerooc.dto.ApplicationStatusDetails;
import com.springcloudstreamkafka.producerooc.dto.StartProcessResponse;
import com.springcloudstreamkafka.producerooc.model.ApplicationLog;
import com.springcloudstreamkafka.producerooc.model.ApplicationProcess;
import com.springcloudstreamkafka.producerooc.repository.ApplicationLogRepository;
import com.springcloudstreamkafka.producerooc.repository.ApplicationProcessRepository;
import com.springcloudstreamkafka.producerooc.service.ApplicationProcessService;
import com.springcloudstreamkafka.producerooc.service.ApplicationResultService;
import com.springcloudstreamkafka.producerooc.service.ApplicationService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/kafka")
public class ApplicationProcessController {

	@NonNull
	private final ApplicationProcessService applicationProcessService;

	@NotNull
	private final ApplicationService applicationService;
	
	@NotNull
	private final ApplicationResultService applicationResultService;

	@NotNull
	private final ApplicationProcessRepository applicationProcessRespository;

	@NotNull
	private final ApplicationLogRepository applicationLogRespository;

	@PostMapping("/start")
	public ResponseEntity<StartProcessResponse> startProcess(@RequestBody ProcessEvent event) {
		log.info("============ Start Processing Single Applicaions at " + ApplicationService.getCurrentTimeString()
				+ "================");

		// Set initial Status for all services.
		applicationService.initProcess(event.getApplicationNumber());

		event.setTime(LocalDateTime.now());
		applicationProcessService.startApplicationProcessing(event);
		
		StartProcessResponse response = StartProcessResponse.builder().message("Kafka Stream Message send successfully!").build();
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/start/{numOfApplication}", method = RequestMethod.GET)
	public ResponseEntity<StartProcessResponse> getActionByApplicationNumber(@PathVariable Integer numOfApplication) {

		log.info("=================== Start Processing Bulk of Applicaions at "
				+ ApplicationService.getCurrentTimeString() + "============");

		for (int i = 1; i <= numOfApplication; i++) {
			String applicationNumber = "F" + i;
			ProcessEvent event = ProcessEvent.builder().applicationNumber(applicationNumber).time(LocalDateTime.now())
					.build();
			applicationProcessService.startApplicationProcessing(event);
		}

		StartProcessResponse response = StartProcessResponse.builder().message("Kafka Stream Message Bulk run started!").build();
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/startRange", method = RequestMethod.GET)
	public ResponseEntity<StartProcessResponse> getActionByApplicationNumber(@RequestParam("startNum") Integer startNum,
			@RequestParam("endNum") Integer endNum) {

		log.info("==================================== Start Processing Bulk of Applicaions at "
				+ ApplicationService.getCurrentTimeString() + "===========================");

		for (int i = startNum; i <= endNum; i++) {
			String applicationNumber = "F" + i;
			ProcessEvent event = ProcessEvent.builder().applicationNumber(applicationNumber).time(LocalDateTime.now())
					.build();
			applicationProcessService.startApplicationProcessing(event);
		}
		StartProcessResponse response = StartProcessResponse.builder().message("Kafka Stream Message Bulk run started!").build();
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "appStatusDetails", method = RequestMethod.GET)
	public ApplicationStatusDetails getApplicationStatusDetails() {
		return applicationService.getAllAppStatus();
	}
	
	@RequestMapping(value = "actions/{applicationNumber}", method = RequestMethod.GET)
	public List<ApplicationProcess> getActionByApplicationNumber(@PathVariable String applicationNumber) {
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

//	@RequestMapping(value = "appResultCounts", method = RequestMethod.GET)
//	public Map<String, Long> getSuccessCounts() {
//		return applicationResultService.getCounts();
//	}
	
	@RequestMapping(value = "appResultCountsByTime", method = RequestMethod.GET)
	public Map<ProcessStatus, Long> getSuccessCountsWindowed(@RequestParam(value = "minutes") Integer mins) {
		return applicationResultService.getCountsByMins(mins);
	}
}
