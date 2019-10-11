package com.springcloudstreamkafka.publisherooc.dto;

import java.time.Duration;
import java.util.List;

import com.kafkastream.demo.lib.model.ProcessStatus;
import com.springcloudstreamkafka.publisherooc.model.ApplicationStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationStatusDetails {
	private List<ApplicationStatus> applicationStatus;
	private Duration processDuration;
	private Long processDurationInSeconds;
	private boolean isProcessFinished;
}
