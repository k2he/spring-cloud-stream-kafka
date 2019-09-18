package com.demo.springcloudstreamkafka.service;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.demo.springcloudstreamkafka.model.ApplicationLog;
import com.demo.springcloudstreamkafka.model.ApplicationProcess;
import com.demo.springcloudstreamkafka.model.ApplicationStatus;
import com.demo.springcloudstreamkafka.model.ProcessEvent;
import com.demo.springcloudstreamkafka.model.ProcessResult;
import com.demo.springcloudstreamkafka.model.ProcessStatus;
import com.demo.springcloudstreamkafka.model.ServiceName;
import com.demo.springcloudstreamkafka.repository.ApplicationLogRepository;
import com.demo.springcloudstreamkafka.repository.ApplicationProcessRepository;
import com.demo.springcloudstreamkafka.repository.ApplicationStatusRepository;

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
		return applicationStatusRespository.saveAndFlush(appStatus);
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
	
	public void addTolog(String applicationNumber, String log) {
		ApplicationLog logEvent = ApplicationLog.builder()
				.applicationNumber(applicationNumber)
				.log(log).build();
		applicationLogRespository.save(logEvent);
	}
	
	// Log event such as trigger start of burea
	public void logProcessEvent(ProcessEvent event) {
		ApplicationStatus appStatus = getAppStatus(event.getApplicationNumber());

		logAppProcess(event.getApplicationNumber(), event.getAction(), event.getActionDesc(), appStatus.getBureaStatus(), appStatus.getAjdcStatus());
	}

	// Log event result such as done burea check sucess/failed
	public void logProcessResult(ProcessResult result) {
		ApplicationStatus appStatus = getAppStatus(result.getApplicationNumber());
		logAppProcess(result.getApplicationNumber(), result.getAction(), result.getActionDesc(), appStatus.getBureaStatus(), appStatus.getAjdcStatus());
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
