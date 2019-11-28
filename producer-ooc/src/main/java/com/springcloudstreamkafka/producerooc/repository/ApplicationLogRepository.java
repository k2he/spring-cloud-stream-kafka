package com.springcloudstreamkafka.producerooc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springcloudstreamkafka.producerooc.model.ApplicationLog;

public interface  ApplicationLogRepository extends JpaRepository<ApplicationLog, Integer> {

	List<ApplicationLog> findByApplicationNumber(String applicationNumber);
}
