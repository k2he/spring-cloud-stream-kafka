package com.demo.springcloudstreamkafka.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "APPLICATION_STATUS")
@EntityListeners(AuditingEntityListener.class)
public class ApplicationStatus {

	@Id
	private String applicationNumber;

	private ProcessStatus bureaStatus;

	private ProcessStatus ajdcStatus;
	
	@LastModifiedDate
	private LocalDateTime lastUpdatedDate;
}
