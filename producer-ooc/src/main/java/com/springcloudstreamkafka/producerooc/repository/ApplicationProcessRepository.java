package com.springcloudstreamkafka.producerooc.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.springcloudstreamkafka.producerooc.model.ApplicationProcess;

public interface  ApplicationProcessRepository extends JpaRepository<ApplicationProcess, Integer> {

	List<ApplicationProcess> findByApplicationNumber(String applicationNumber);
}
