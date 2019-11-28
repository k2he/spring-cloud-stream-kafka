package com.springcloudstreamkafka.producerooc.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kafkastream.demo.lib.model.ProcessStatus;

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

	@Enumerated(EnumType.STRING)
	private ProcessStatus bureauStatus;

	@Enumerated(EnumType.STRING)
	private ProcessStatus ajdcStatus;
	
	@LastModifiedDate
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastUpdatedDate;
	
	@CreatedDate
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
}
