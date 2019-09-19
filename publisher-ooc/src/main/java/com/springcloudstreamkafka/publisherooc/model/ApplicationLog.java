package com.springcloudstreamkafka.publisherooc.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "APPLICATION_LOG")
@EntityListeners(AuditingEntityListener.class)
public class ApplicationLog {
	
	@Id
	@GeneratedValue
    private int id;

	private String applicationNumber;
	
	private String log;
	
	@CreatedDate
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.ms")
	private LocalDateTime createdDate;
}
