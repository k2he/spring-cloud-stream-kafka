package com.springcloudstreamkafka.consumertsys.listener;
		
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import com.kafkastream.demo.lib.model.ProcessEvent;
import com.kafkastream.demo.lib.model.ProcessResult;
import com.kafkastream.demo.lib.model.ProcessStatus;
import com.kafkastream.demo.lib.model.ServiceName;
import com.springcloudstreamkafka.consumertsys.stream.ApplicationProcessStreams;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class TSYSListener {

	@NonNull
	private final ApplicationProcessStreams applicationProcessStreams;

	@StreamListener(ApplicationProcessStreams.TSYS_INPUT)
	public void handleAjdcStart(@Payload ProcessEvent event) {
		log.info("TSYS event: {}", event);

		// delay 1 seconds
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			System.err.format("IOException: %s%n", e);
		}

		ProcessResult result = ProcessResult.builder().action("TSYS-completed")
				.actionDesc("TSYS Service Completed, sending message back to Queue with Status Completed.")
				.serviceName(ServiceName.TSYS).status(ProcessStatus.COMPLETED)
				.applicationNumber(event.getApplicationNumber()).time(LocalDateTime.now()).build();

		MessageChannel messageChannel = applicationProcessStreams.outboundProcessResult();
		messageChannel.send(MessageBuilder.withPayload(result)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());

	}
}
