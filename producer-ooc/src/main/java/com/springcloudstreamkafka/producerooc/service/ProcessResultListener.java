package com.springcloudstreamkafka.producerooc.service;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.kafkastream.demo.lib.model.ProcessEvent;
import com.kafkastream.demo.lib.model.ProcessResult;
import com.kafkastream.demo.lib.model.ProcessStatus;
import com.kafkastream.demo.lib.model.ServiceName;
import com.springcloudstreamkafka.producerooc.model.ApplicationStatus;
import com.springcloudstreamkafka.producerooc.stream.ApplicationProcessStreams;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessResultListener {

	@NonNull
	private final ApplicationProcessStreams applicationProcessStreams;
	
	@NotNull
	private final ApplicationService applicationService;
	
	@NotNull
	private final ApplicationStreamingService applicationStreamingService;

	@StreamListener(ApplicationProcessStreams.PROCESS_RESULT_INPUT)
	public void handleProcessFinished(@Payload ProcessResult result) {
		applicationService.logProcessResult(result);

		ApplicationStatus appStatus = applicationService.updateAppStatus(result);

		log.info("(" + result.getApplicationNumber() + ") Burea Status: " + appStatus.getBureauStatus()
				+ " Ajudication Status: " + appStatus.getAjdcStatus());
		// CASE #1: Success
		if (appStatus.getBureauStatus().equals(ProcessStatus.COMPLETED)
				&& appStatus.getAjdcStatus().equals(ProcessStatus.COMPLETED)
				&& appStatus.getTsysStatus().equals(ProcessStatus.COMPLETED)) {
			
			applicationService.addTolog(result.getApplicationNumber(), "Application (" + result.getApplicationNumber()
					+ ") Process Done at " + ApplicationService.getCurrentTimeString());
			applicationStreamingService.sendApplicationResult(result.getApplicationNumber(), ProcessStatus.COMPLETED);
		} else if (appStatus.getAjdcStatus().equals(ProcessStatus.FAILED)
				|| appStatus.getBureauStatus().equals(ProcessStatus.FAILED) 
				|| appStatus.getTsysStatus().equals(ProcessStatus.FAILED)
				) {// CASE # 2 Failed
			
			applicationService.addTolog(result.getApplicationNumber(), "Application Failed (" + result.getApplicationNumber()
			+ ") Process Done at " + ApplicationService.getCurrentTimeString());
			applicationStreamingService.sendApplicationResult(result.getApplicationNumber(), ProcessStatus.FAILED);
		} else if (appStatus.getBureauStatus().equals(ProcessStatus.COMPLETED) 
				&& appStatus.getTsysStatus().equals(ProcessStatus.COMPLETED)){ // If both Bureau and TSYS completed, then start processing Adjudication
			processAjudication(result.getApplicationNumber());
		}
	}
	
	private void processAjudication(String applicationNumber) {
		ProcessEvent newEvent = ProcessEvent.builder().serviceName(ServiceName.AJUDCATION).action("ADJC-start")
				.actionDesc("Message send to Message Queue to trigger ADJC Service")
				.applicationNumber(applicationNumber).time(LocalDateTime.now()).build();

		// log the event
		applicationService.logProcessEvent(newEvent);
		applicationService.addTolog(applicationNumber,
				applicationNumber + " Send Message to Message Queue to Start ADJCS Service.");

		MessageChannel messageChannel = applicationProcessStreams.outboundAjdc();
		messageChannel.send(MessageBuilder.withPayload(newEvent)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
	}
}
