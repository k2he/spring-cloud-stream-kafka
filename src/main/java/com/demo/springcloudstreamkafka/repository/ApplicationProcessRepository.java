package com.demo.springcloudstreamkafka.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.springcloudstreamkafka.model.ApplicationProcess;

public interface  ApplicationProcessRepository extends JpaRepository<ApplicationProcess, Integer> {

	List<ApplicationProcess> findByApplicationNumber(String applicationNumber);
}
