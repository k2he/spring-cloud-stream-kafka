package com.springcloudstreamkafka.publisherooc.service;

import javax.validation.constraints.NotNull;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import com.springcloudstreamkafka.lib.dto.ProcessEvent;
import com.springcloudstreamkafka.lib.dto.ServiceName;
import com.springcloudstreamkafka.publisherooc.stream.ApplicationProcessStreams;
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
		log.info("Started Processing Applcation {}", event);
		//Set initial Status for all services.
		applicationService.initProcess(event.getApplicationNumber());
				
		ProcessEvent newEvent = ProcessEvent.builder().serviceName(ServiceName.BUREAU)
				.action("Bureau-start")
				.actionDesc("Message send to Message Queue with to trigger Bureau Service")
				.applicationNumber(event.getApplicationNumber())
				.time(event.getTime()).build();
				
		//log the event
		applicationService.logProcessEvent(newEvent);
		applicationService.addTolog(event.getApplicationNumber(), "Send Message to Message Queue to Start Bureau Service.");
		
		MessageChannel messageChannel = applicationProcessStreams.outboundBureau();
		messageChannel.send(MessageBuilder.withPayload(newEvent)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());

		newEvent = ProcessEvent.builder().serviceName(ServiceName.AJUDCATION)
				.action("Ajudication-start")
				.actionDesc("Message send to Message Queue to trigger Ajudication Service")
				.applicationNumber(event.getApplicationNumber())
				.time(event.getTime()).build();

		//log the event
		applicationService.logProcessEvent(newEvent);
		applicationService.addTolog(event.getApplicationNumber(), "Send Message to Message Queue to Start Ajudication Service.");
		
		
		messageChannel = applicationProcessStreams.outboundAjdc();
		messageChannel.send(MessageBuilder.withPayload(newEvent)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
	}

}
