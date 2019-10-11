package com.springcloudstreamkafka.publisherooc.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StartProcessResponse {
	private String message;
}
