package com.springcloudstreamkafka.publisherooc.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.validation.constraints.NotNull;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.apache.kafka.streams.state.WindowStoreIterator;
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
import com.springcloudstreamkafka.publisherooc.stream.ApplicationProcessStreams;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ApplicationResultService {

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
		// KTable<Windowed<String> , Long> kTable = applicationResultStream.filter((key,
		// value) -> value.equals(ProcessStatus.COMPLETED))
		// .map((key, value) -> new KeyValue<String,
		// String>(value.getApplicationNumber(), "0"))
		// .groupByKey()
		// .windowedBy(TimeWindows.of(1000 * 60))
		// .count(Materialized.as(ApplicationProcessStreams.SUCCESS_COUNT_MATERALIZED_VIEW));

		// Below Code works

		// Create a window state store contains the application process success or failed counts for 5 minute
		KTable<Windowed<String>, Long> kTable = applicationResultStream
				.map((key, value) -> {
//					log.info(value.getApplicationNumber() + " " + value.getStatus());
					return new KeyValue<String, String>(value.getStatus().toString(), "0");
				})
				.groupByKey()
				.windowedBy(TimeWindows.of(Duration.ofMinutes(5).toMillis()))
				.count(Materialized.as(ApplicationProcessStreams.APP_RESULT_COUNT_MATERALIZED_VIEW));

		// KTable<String, Long> kTable = applicationResultStream
		// .map((key, value) -> {
		// log.info(value.getApplicationNumber() + " " + value.getStatus());
		// return new KeyValue<String, String>(value.getStatus().toString(), "0");
		// })
		// .groupByKey()
		// .count(Materialized.as(ApplicationProcessStreams.SUCCESS_COUNT_MATERALIZED_VIEW));

		kTable.toStream().foreach((key, value) -> log.info(key + " = " + value));
	}

	public Map<String, Long> getCounts() {
		Map<String, Long> counts = new HashMap<>();

		ReadOnlyKeyValueStore<String, Long> countStore = interactiveQueryService.getQueryableStore(
				ApplicationProcessStreams.APP_RESULT_COUNT_MATERALIZED_VIEW,
				QueryableStoreTypes.<String, Long>keyValueStore());

		KeyValueIterator<String, Long> all = countStore.all();

		if (all != null) {
			while (all.hasNext()) {
				KeyValue<String, Long> value = all.next();
				counts.put(value.key, value.value);
			}
			return counts;
		}
		return null;
	}

	public Map<ProcessStatus, Long> getCountsWindowedForPastMins(int mins) {
		Map<ProcessStatus, Long> counts = new HashMap<>();

//		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//		c.add(Calendar.HOUR, -24);
//		long result = c.getTimeInMillis();
		
		long timeFrom = System.currentTimeMillis() - 1000 * 60 * mins; // beginning of time = 5 mins ago
		long timeTo = System.currentTimeMillis(); // now (in processing-time)

		ReadOnlyWindowStore<String, Long> countStore = interactiveQueryService.getQueryableStore(
				ApplicationProcessStreams.APP_RESULT_COUNT_MATERALIZED_VIEW,
				QueryableStoreTypes.<String, Long>windowStore());

		WindowStoreIterator<Long> iterator = countStore.fetch(ProcessStatus.COMPLETED.toString(), timeFrom, timeTo);
//		KeyValueIterator<Windowed<String>, Long> iterator = countStore.all();
		
		Long successCount = null;
		while (iterator.hasNext()) {
			KeyValue<Long, Long> next = iterator.next();
//			KeyValue<Windowed<String>, Long> next = iterator.next();
//			Windowed<String> windowedKey = next.key; 
			Long windowedKey = next.key;
			successCount = next.value;
			log.info("!!! Key = " + windowedKey + "  Value = " + successCount);
		}
		
		if (successCount != null) {
			counts.put(ProcessStatus.COMPLETED, successCount);
		}
		
		// Get count for Failed Application
		WindowStoreIterator<Long> iteratorFailed = countStore.fetch(ProcessStatus.FAILED.toString(), timeFrom, timeTo);
		
		Long failedCount = null;
		while (iteratorFailed.hasNext()) {
			KeyValue<Long, Long> next = iteratorFailed.next();
//			KeyValue<Windowed<String>, Long> next = iterator.next();
//			Windowed<String> windowedKey = next.key; 
			Long windowedKey = next.key;
			failedCount = next.value;
			log.info("!!! Key = " + windowedKey + "  Value = " + failedCount);
		}
		
		if (failedCount != null) {
			counts.put(ProcessStatus.FAILED, failedCount);
		}
		
		return counts;
	}
}
