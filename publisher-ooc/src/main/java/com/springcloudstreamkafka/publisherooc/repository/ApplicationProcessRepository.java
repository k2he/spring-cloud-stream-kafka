package com.springcloudstreamkafka.publisherooc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springcloudstreamkafka.publisherooc.model.ApplicationProcess;

public interface  ApplicationProcessRepository extends JpaRepository<ApplicationProcess, Integer> {

	List<ApplicationProcess> findByApplicationNumber(String applicationNumber);
}
