package com.demo.springcloudstreamkafka.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Greetings {
  private LocalDateTime time;
  private String message;
  private String sender;
}
