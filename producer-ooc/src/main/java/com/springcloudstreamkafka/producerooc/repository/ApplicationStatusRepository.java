package com.springcloudstreamkafka.producerooc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springcloudstreamkafka.producerooc.model.ApplicationStatus;

public interface  ApplicationStatusRepository extends JpaRepository<ApplicationStatus, String> {
	public List<ApplicationStatus> findAllByOrderByLastUpdatedDateAsc();
	public List<ApplicationStatus> findAllByOrderByCreatedDateAsc();
}
