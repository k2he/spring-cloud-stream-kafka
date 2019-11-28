package com.springcloudstreamkafka.producerooc.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
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
@Table(name = "APPLICATION_PROCESS")
@EntityListeners(AuditingEntityListener.class)
public class ApplicationProcess {
	
	@Id
	@GeneratedValue
    private int id;

	private String applicationNumber;
	
	private String applicationAction;
	
	private String applicationDesc;
	
	@Enumerated(EnumType.STRING)
	private ProcessStatus bureauStatus;
	
	@Enumerated(EnumType.STRING)
	private ProcessStatus ajdcStatus;
	
	@Enumerated(EnumType.STRING)
	private ProcessStatus tsysStatus;
	
	private String log;
	
	@CreatedDate
	private LocalDateTime createdDate;
}
