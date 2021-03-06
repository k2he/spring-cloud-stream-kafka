package com.springcloudstreamkafka.producerooc.service;

import javax.validation.constraints.NotNull;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import com.kafkastream.demo.lib.model.ProcessEvent;
import com.kafkastream.demo.lib.model.ServiceName;
import com.springcloudstreamkafka.producerooc.stream.ApplicationProcessStreams;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationProcessService {

	@NonNull
	private final ApplicationProcessStreams applicationProcessStreams;

	@NotNull
	private final ApplicationService applicationService;

	public void startApplicationProcessing(final ProcessEvent event) {
		log.info("Start Processing " + event.getApplicationNumber());
		// Set initial Status for all services.
		applicationService.initProcess(event.getApplicationNumber());

		ProcessEvent newEvent = ProcessEvent.builder().serviceName(ServiceName.BUREAU).action("Bureau-start")
				.actionDesc("Message send to Message Queue with to trigger Bureau Service")
				.applicationNumber(event.getApplicationNumber()).time(event.getTime()).build();

		// log the event
		applicationService.logProcessEvent(newEvent);
		applicationService.addTolog(event.getApplicationNumber(),
				event.getApplicationNumber() + " Send Message to Message Queue to Start Bureau Service.");

		MessageChannel messageChannel = applicationProcessStreams.outboundBureau();
		messageChannel.send(MessageBuilder.withPayload(newEvent)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());

		newEvent = ProcessEvent.builder().serviceName(ServiceName.TSYS).action("TYSY-start")
				.actionDesc("Message send to Message Queue to trigger TSYS Service")
				.applicationNumber(event.getApplicationNumber()).time(event.getTime()).build();

		// log the event
		applicationService.logProcessEvent(newEvent);
		applicationService.addTolog(event.getApplicationNumber(),
				event.getApplicationNumber() + " Send Message to Message Queue to Start TSYS Service.");

		messageChannel = applicationProcessStreams.outboundTsys();
		messageChannel.send(MessageBuilder.withPayload(newEvent)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
	}

}
