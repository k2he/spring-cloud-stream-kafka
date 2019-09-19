package com.kafkastream.demo.lib.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessEvent {
  private ServiceName serviceName;
  private String applicationNumber;
  private String action;
  private String actionDesc;
  private LocalDateTime time;
}
