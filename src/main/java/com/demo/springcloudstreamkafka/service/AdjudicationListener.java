package com.demo.springcloudstreamkafka.service;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import com.demo.springcloudstreamkafka.model.ProcessEvent;
import com.demo.springcloudstreamkafka.model.ProcessResult;
import com.demo.springcloudstreamkafka.model.ProcessStatus;
import com.demo.springcloudstreamkafka.model.ServiceName;
import com.demo.springcloudstreamkafka.stream.ApplicationProcessStreams;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AdjudicationListener {
  
	@NonNull
	private final ApplicationProcessStreams applicationProcessStreams;
	
	@NotNull
	private final ApplicationService applicationService;
	
	@StreamListener(ApplicationProcessStreams.AJDC_INPUT)
	public void handleAjdcStart(@Payload ProcessEvent event) {
		log.info("Ajudication event: {}", event);
		applicationService.updateAppStatus(event.getApplicationNumber(), "update-ajudication-status", null, ProcessStatus.INPROGRESS);
		
		applicationService.addTolog(event.getApplicationNumber(), "Ajudication Service recieved event. Starting Bureau Service - " + event.toString());
		
		ProcessResult result = ProcessResult.builder()
				.action("ajudication-compileted")
				.actionDesc("Ajudication Service Completed, sending message back to Queue with Status Completed.")
				.serviceName(ServiceName.AJUDCATION)
				.status(ProcessStatus.COMPLETED)
				.applicationNumber(event.getApplicationNumber())
				.time(LocalDateTime.now()).build();
		
		applicationService.logProcessResult(result);
		applicationService.addTolog(event.getApplicationNumber(), "Ajudication Service finished. Sending message back to message Queue");
		
		
		MessageChannel messageChannel = applicationProcessStreams.outboundProcessResult();
	    messageChannel.send(MessageBuilder.withPayload(result)
	        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
	    
	}
}
