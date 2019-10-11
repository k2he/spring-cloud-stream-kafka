
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';

import { ApplicationProcessStatus, ApplicationStartResponse } from '../resources/applicationProcessStatus';
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

    // public getProjectInfoById(id: number): Observable<ProjectInfo> {
    //     return this.http.get<ProjectInfo>(`${KafkaService.ROUTES.kafka}/${id}`);
    // }

    // public deleteProjectInfoById(id: string | number): Observable<ProjectInfo> {
    //     return this.http.delete<ProjectInfo>(`${KafkaService.ROUTES.kafka}/start/${id}`);
    // }

    // public updateProjectInfo(info: ProjectInfo) {
    //     return this.http.post<ProjectInfo>(KafkaService.ROUTES.kafka, info);
    // }
}
