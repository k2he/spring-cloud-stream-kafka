import { Component, OnInit, ViewChild, AfterViewChecked } from '@angular/core';
import { MatTableDataSource, MatSort } from '@angular/material';
import { timer, Observable, Subject } from 'rxjs';
import { switchMap, takeUntil, catchError } from 'rxjs/operators';

import { KafkaService } from '../api/kafka.service';
import { ApplicationProcessStatus } from '../resources/applicationProcessStatus';
@Component({
  selector: 'app-kafka',
  templateUrl: './kafka.component.html',
  styleUrls: ['./kafka.component.scss']
})
export class KafkaComponent implements OnInit, AfterViewChecked {

  testProcessStartedMessage: string;
  isFinished: boolean;
  durationSeconds: number;

  private fetchData$:  Observable<ApplicationProcessStatus> = this.kafkaService.getApplicationsProcessDetailData();
  private refreshInterval$: Observable<ApplicationProcessStatus>;
  private killTrigger: Subject<void> = new Subject();

  displayedColumns = ['applicationNumber', 'bureauStatus', 'ajdcStatus', 'createdDate', 'lastUpdatedDate'];
  dataSource;

  @ViewChild(MatSort) sort: MatSort;

  constructor(private kafkaService: KafkaService) { }

  ngOnInit() {
    this.fetchData();
  }

  fetchData() {
    // getApplicationsProcessDetailData();
  }

  ngAfterViewChecked() {
    if (this.dataSource) {
        this.dataSource.sort = this.sort;
    }
  }

  async testProcess20Apps() {
    this.testProcessStartedMessage = null;
    this.isFinished = false;
    const response = await this.kafkaService.startProcessApps(20).toPromise();
    this.testProcessStartedMessage = response.message;

    timer(0, 2000)
    .pipe(
      // This kills the request if the user closes the component 
      takeUntil(this.killTrigger),
      // switchMap cancels the last request, if no response have been received since last tick
      switchMap(() => this.fetchData$)
    ).subscribe(
      data => this.setApplicationProcessStatusData(data)
    );
  }

  async getApplicationsProcessDetailData() {
    const response = await this.kafkaService.getApplicationsProcessDetailData().toPromise();
    this.setApplicationProcessStatusData(response);
  }

  setApplicationProcessStatusData(appStatus: ApplicationProcessStatus) {
    this.isFinished = appStatus.processFinished;
    if (this.isFinished) {
      this.killTrigger.next();
    }
    this.durationSeconds = appStatus.processDurationInSeconds;
    
    this.dataSource = new MatTableDataSource(appStatus.applicationStatus);
  }

  getApplicationsProcessSummary() {

  }

  ngOnDestroy(){
    this.killTrigger.next();
  }
}
