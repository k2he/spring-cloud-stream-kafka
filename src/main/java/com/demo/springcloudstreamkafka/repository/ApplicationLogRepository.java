package com.demo.springcloudstreamkafka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.springcloudstreamkafka.model.ApplicationLog;

public interface  ApplicationLogRepository extends JpaRepository<ApplicationLog, Integer> {

	List<ApplicationLog> findByApplicationNumber(String applicationNumber);
}
