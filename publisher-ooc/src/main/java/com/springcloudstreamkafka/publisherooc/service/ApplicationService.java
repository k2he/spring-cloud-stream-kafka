package com.springcloudstreamkafka.publisherooc.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.kafkastream.demo.lib.model.ProcessEvent;
import com.kafkastream.demo.lib.model.ProcessResult;
import com.kafkastream.demo.lib.model.ProcessStatus;
import com.kafkastream.demo.lib.model.ServiceName;
import com.springcloudstreamkafka.publisherooc.model.ApplicationLog;
import com.springcloudstreamkafka.publisherooc.model.ApplicationProcess;
import com.springcloudstreamkafka.publisherooc.model.ApplicationStatus;
import com.springcloudstreamkafka.publisherooc.repository.ApplicationLogRepository;
import com.springcloudstreamkafka.publisherooc.repository.ApplicationProcessRepository;
import com.springcloudstreamkafka.publisherooc.repository.ApplicationStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ApplicationService {

	@NotNull
	private final ApplicationProcessRepository applicationProcessRespository;

	@NotNull
	private final ApplicationStatusRepository applicationStatusRespository;

	@NotNull
	private final ApplicationLogRepository applicationLogRespository;
	
	public ApplicationStatus initProcess(String applicationNumber) {
		ApplicationStatus appStatus = ApplicationStatus.builder().applicationNumber(applicationNumber)
				.ajdcStatus(ProcessStatus.PENDING).bureaStatus(ProcessStatus.PENDING).build();
		return applicationStatusRespository.save(appStatus);
	}

	@Transactional(isolation=Isolation.SERIALIZABLE)
	public ApplicationStatus getAppStatus(String applicationNumber) {
		return applicationStatusRespository.findById(applicationNumber).orElse(null);
	}

	@Transactional(isolation=Isolation.SERIALIZABLE)
	public ApplicationStatus updateAppStatus(ProcessResult result) {
		if (result.getServiceName().equals(ServiceName.BUREAU)) {
			return updateAppStatus(result.getApplicationNumber(), "update-burea-status", result.getStatus(), null);
		} else {
			return updateAppStatus(result.getApplicationNumber(), "update-ajdudication-status", null, result.getStatus());
		}
	}
	
	@Transactional(isolation=Isolation.SERIALIZABLE)
	public ApplicationStatus updateAppStatus(String applicationNumber, String action, ProcessStatus bureaStatus, ProcessStatus ajdcStatus) {
		ApplicationStatus appStatus = getAppStatus(applicationNumber);
		if (appStatus == null) {
			appStatus = initProcess(applicationNumber);
		} else {
			if (bureaStatus != null) {
				appStatus.setBureaStatus(bureaStatus);
			} else if (ajdcStatus != null) {
				appStatus.setAjdcStatus(ajdcStatus);
			}
			
			if (bureaStatus != null && ajdcStatus != null) {
				appStatus = applicationStatusRespository.saveAndFlush(appStatus);
			}
		}
		
		logAppProcess(applicationNumber, action, "Updating Service static in Database", appStatus.getBureaStatus(), appStatus.getAjdcStatus());
		
		return appStatus;
	}
	
	public void addTolog(String applicationNumber, String logString) {
		log.info(logString);
		ApplicationLog logEvent = ApplicationLog.builder()
				.applicationNumber(applicationNumber)
				.log(logString).build();
		applicationLogRespository.save(logEvent);
	}
	
	// Log event such as trigger start of burea
	public void logProcessEvent(ProcessEvent event) {
		ApplicationStatus appStatus = getAppStatus(event.getApplicationNumber());
		if (appStatus!=null) {
			logAppProcess(event.getApplicationNumber(), event.getAction(), event.getActionDesc(), appStatus.getBureaStatus(), appStatus.getAjdcStatus());
		}
	}
	// Log event result such as done burea check sucess/failed
	public void logProcessResult(ProcessResult result) {
	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:ms");
	  String time = result.getTime().format(formatter);
	  log.debug("(" + result.getApplicationNumber() + ") " + result.getServiceName() + ", "+ result.getAction() + ", " + result.getStatus() + ", " +  time);
		ApplicationStatus appStatus = getAppStatus(result.getApplicationNumber());
		if (appStatus!=null) {
			logAppProcess(result.getApplicationNumber(), result.getAction(), result.getActionDesc(), appStatus.getBureaStatus(), appStatus.getAjdcStatus());
		}
	}
	
	public void logAppProcess(String applicationNumber, String applicationAction, String applicationDesc, ProcessStatus bureaStatus, ProcessStatus ajdcStatus) {
		ApplicationProcess process = ApplicationProcess.builder()
				.applicationNumber(applicationNumber)
				.applicationAction(applicationAction)
				.applicationDesc(applicationDesc)
				.bureaStatus(bureaStatus)
				.ajdcStatus(ajdcStatus)
				.build();
		applicationProcessRespository.save(process);
	}
}
