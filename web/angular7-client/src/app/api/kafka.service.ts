
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';

import { ApplicationProcessStatus, ApplicationStartResponse, ProcessingSummary } from '../resources/applicationProcessStatus';
import APIROUTES from '../config/api-routes';

@Injectable({
    providedIn: 'root',
})
export class KafkaService {

    public static ROUTES = {
        kafka: `${environment.apiPath}${APIROUTES.kafka}`
    };

    constructor(private http: HttpClient) { }

    public startProcessApps(numberOfApps: string | number): Observable<ApplicationStartResponse> {
        return this.http.get<ApplicationStartResponse>(`${KafkaService.ROUTES.kafka}/start/${numberOfApps}`);
    }

    public getApplicationsProcessDetailData(): Observable<ApplicationProcessStatus> {
        return this.http.get<ApplicationProcessStatus>(`${KafkaService.ROUTES.kafka}/appStatusDetails`);
    }

    public getProcessingSummary(mins: number): Observable<ProcessingSummary> {
        return this.http.get<ProcessingSummary>(`${KafkaService.ROUTES.kafka}/appResultCountsByTime?minutes=${mins}`);
    }
}
