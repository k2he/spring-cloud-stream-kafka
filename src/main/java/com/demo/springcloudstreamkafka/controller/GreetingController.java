package com.demo.springcloudstreamkafka.controller;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.demo.springcloudstreamkafka.model.Greetings;
import com.demo.springcloudstreamkafka.service.GreetingsService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GreetingController {

  @NonNull
  private final GreetingsService greetingsService;

  @PostMapping("/greetings")
  public ResponseEntity<String> greetings(@RequestBody Greetings greeting) {
    greeting.setTime(LocalDateTime.now());
    greetingsService.sendGreeting(greeting);
    return new ResponseEntity<>("Kafka Stream Message send successfully!", HttpStatus.OK);
  }
}
