
import { throwError, Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { HttpResponse } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

import { ProjectInfo } from '../resources/project';
import APIROUTES from '../config/api-routes';

@Injectable({
    providedIn: 'root',
})
export class ProjectService {

    public static ROUTES = {
        projects: `${environment.apiPath}${APIROUTES.projects}`
    };

    constructor(private http: HttpClient) { }

    public getAllProjects(): Observable<ProjectInfo[]> {
        return this.http.get<ProjectInfo[]>(ProjectService.ROUTES.projects);
    }

    public createProjectInfo(info: ProjectInfo): Observable<ProjectInfo> {
        return this.http.post<ProjectInfo>(ProjectService.ROUTES.projects, info);
    }

    public getProjectInfoById(id: number): Observable<ProjectInfo> {
        return this.http.get<ProjectInfo>(`${ProjectService.ROUTES.projects}/${id}`);
    }

    public deleteProjectInfoById(id: string | number): Observable<ProjectInfo> {
        return this.http.delete<ProjectInfo>(`${ProjectService.ROUTES.projects}/${id}`);
    }

    public updateProjectInfo(info: ProjectInfo) {
        return this.http.post<ProjectInfo>(ProjectService.ROUTES.projects, info);
    }
}
