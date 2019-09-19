package com.springcloudstreamkafka.publisherooc.service;

import javax.validation.constraints.NotNull;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.springcloudstreamkafka.lib.dto.ProcessResult;
import com.springcloudstreamkafka.lib.dto.ProcessStatus;
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
		log.info("Process Result: {}", result);
		applicationService.addTolog(result.getApplicationNumber(), result.getServiceName() + " recieved event. Starting Bureau Service - " + result.toString());
		
		applicationService.logProcessResult(result);

		ApplicationStatus appStatus = applicationService.updateAppStatus(result);
		
		log.info("Burea Status: " + appStatus.getBureaStatus() + " Ajudication Status: " + appStatus.getAjdcStatus());
		if (appStatus.getBureaStatus().equals(ProcessStatus.COMPLETED) && appStatus.getAjdcStatus().equals(ProcessStatus.COMPLETED)) {
			log.info("Both Burea and Ajudication done");
			applicationService.addTolog(result.getApplicationNumber(), "Both Burea and Ajudication done. Can start next process depends on both service completion.");
			
		}
	}
}
