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
public class ApplicationResultEvent {
  private String applicationNumber;
  private ProcessStatus status;
  private String actionDesc;
  private LocalDateTime time;
}
