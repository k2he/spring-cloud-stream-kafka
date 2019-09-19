package com.springcloudstreamkafka.consumerburea.listener;

import java.time.LocalDateTime;
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
import com.springcloudstreamkafka.consumerburea.stream.ApplicationProcessStreams;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class BureauListener {
  
	@NonNull
	private final ApplicationProcessStreams applicationProcessStreams;
	
	@StreamListener(ApplicationProcessStreams.BUREAU_INPUT)
	public void handleBureauStart(@Payload ProcessEvent event) {
		log.info("Start Bureau event: {}", event);
		
		// delay 5 seconds
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
            System.err.format("IOException: %s%n", e);
        }
        
		// Business Logic finished, send response to message queue.
		ProcessResult result = ProcessResult.builder()
				.action("bureau-compileted")
				.actionDesc("Bureau Service Completed, sending message back to Queue with Status Completed.")
				.serviceName(ServiceName.BUREAU)
				.status(ProcessStatus.COMPLETED)
				.applicationNumber(event.getApplicationNumber())
				.time(LocalDateTime.now()).build();
		
		log.info("Finished Bureau process for application=" + result.getApplicationNumber() + "\n");
		MessageChannel messageChannel = applicationProcessStreams.outboundProcessResult();
	    messageChannel.send(MessageBuilder.withPayload(result)
	        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
	}
}
