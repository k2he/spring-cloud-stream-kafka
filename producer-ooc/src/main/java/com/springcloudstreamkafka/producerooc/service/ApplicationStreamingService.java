package com.springcloudstreamkafka.producerooc.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MimeTypeUtils;

import com.kafkastream.demo.lib.model.ApplicationResultEvent;
import com.kafkastream.demo.lib.model.ProcessStatus;
import com.springcloudstreamkafka.producerooc.stream.ApplicationProcessStreams;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ApplicationStreamingService {

	@NonNull
	private final ApplicationProcessStreams applicationProcessStreams;

	@NotNull
	private final ApplicationService applicationService;

	@NotNull
	private final InteractiveQueryService interactiveQueryService;

	public void sendApplicationResult(String applicationNumber, ProcessStatus status) {
		ApplicationResultEvent appResultEvent = ApplicationResultEvent.builder().applicationNumber(applicationNumber)
				.actionDesc("Application in Success or Fail status").status(status).time(LocalDateTime.now()).build();

		MessageChannel messageChannel = applicationProcessStreams.outboundApplicationResult();
		messageChannel.send(MessageBuilder.withPayload(appResultEvent)
				.setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build());
	}

	@StreamListener(ApplicationProcessStreams.APPLICATION_RESULT_IN)
	public void process(KStream<String, ApplicationResultEvent> applicationResultStream) {
		// Create a window state store contains the application process success or failed counts for 5 minute
		KTable<Windowed<String>, Long> kTable = applicationResultStream
				.map((key, value) -> {
//					log.info(value.getApplicationNumber() + " " + value.getStatus());
					return new KeyValue<String, String>(value.getStatus().toString(), "0");
				})
				.groupByKey()
				.windowedBy(TimeWindows.of(Duration.ofMinutes(5).toMillis()))
				.count(Materialized.as(ApplicationProcessStreams.APP_RESULT_COUNT_MATERALIZED_VIEW));

		// Below Code will get all count for everything
		// KTable<String, Long> kTable = applicationResultStream
		// .map((key, value) -> {
		// log.info(value.getApplicationNumber() + " " + value.getStatus());
		// return new KeyValue<String, String>(value.getStatus().toString(), "0");
		// })
		// .groupByKey()
		// .count(Materialized.as(ApplicationProcessStreams.SUCCESS_COUNT_MATERALIZED_VIEW));

		kTable.toStream().foreach((key, value) -> log.info(key + " = " + value));
	}

	// Get total number of success and failed application counts
	public Map<ProcessStatus, Long> getCountsByMins(int mins) {
		Map<ProcessStatus, Long> counts = new HashMap<>();
		
		long timeFrom = System.currentTimeMillis() - 1000 * 60 * mins; // beginning of time = # of mins ago
		long timeTo = System.currentTimeMillis(); // now (in processing-time)

		ReadOnlyWindowStore<String, Long> countStore = interactiveQueryService.getQueryableStore(
				ApplicationProcessStreams.APP_RESULT_COUNT_MATERALIZED_VIEW,
				QueryableStoreTypes.<String, Long>windowStore());

		KeyValueIterator<Windowed<String>, Long> iterator = countStore.fetchAll(timeFrom, timeTo);
		
		Long successCount = new Long(0);
		Long failedCount = new Long(0);
		
		// Sum of each window is total number of success/ failure over past period of time 
		// (Remember we may need set WindowTime to be small in order to make sure there is no duplicate.
		while (iterator.hasNext()) {		
			KeyValue<Windowed<String>, Long> next = iterator.next();
			Windowed<String> windowedKey = next.key; 
			String key = windowedKey.key();
			if (ProcessStatus.COMPLETED.toString().equals(key)) {
				successCount += next.value;
			} else {
				failedCount += next.value;
			}
			log.info("!!! Key = " + windowedKey.key() + "  Value = " + next.value);
		}
		
		counts.put(ProcessStatus.COMPLETED, successCount);
		counts.put(ProcessStatus.FAILED, failedCount);
		
		return counts;
	}
	
	//Below Code will get all count for everything
//	public Map<String, Long> getCounts() {
//		Map<String, Long> counts = new HashMap<>();
//
//		ReadOnlyKeyValueStore<String, Long> countStore = interactiveQueryService.getQueryableStore(
//				ApplicationProcessStreams.APP_RESULT_COUNT_MATERALIZED_VIEW,
//				QueryableStoreTypes.<String, Long>keyValueStore());
//
//		KeyValueIterator<String, Long> all = countStore.all();
//
//		if (all != null) {
//			while (all.hasNext()) {
//				KeyValue<String, Long> value = all.next();
//				counts.put(value.key, value.value);
//			}
//			return counts;
//		}
//		return null;
//	}
}
