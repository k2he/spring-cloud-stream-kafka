package com.springcloudstreamkafka.publisherooc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.springcloudstreamkafka.publisherooc.model.ApplicationStatus;

public interface  ApplicationStatusRepository extends JpaRepository<ApplicationStatus, String> {

}
