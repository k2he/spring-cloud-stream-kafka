package com.springcloudstreamkafka.publisherooc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springcloudstreamkafka.publisherooc.model.ApplicationLog;

public interface  ApplicationLogRepository extends JpaRepository<ApplicationLog, Integer> {

	List<ApplicationLog> findByApplicationNumber(String applicationNumber);
}
