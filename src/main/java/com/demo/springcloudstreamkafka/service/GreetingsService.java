package com.demo.springcloudstreamkafka.service;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import com.demo.springcloudstreamkafka.model.Greetings;
import com.demo.springcloudstreamkafka.stream.GreetingsStreams;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class GreetingsService {
  @NonNull
  private final GreetingsStreams greetingsStreams;

  public void sendGreeting(final Greetings greetings) {
    log.info("Sending Greetings {}", greetings);
    MessageChannel messageChannel = greetingsStreams.outboundGreetings();
    messageChannel.send(MessageBuilder.withPayload(greetings)
        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
  }

}
