package com.demo.springcloudstreamkafka.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.springcloudstreamkafka.model.ApplicationStatus;

public interface  ApplicationStatusRepository extends JpaRepository<ApplicationStatus, String> {

}
