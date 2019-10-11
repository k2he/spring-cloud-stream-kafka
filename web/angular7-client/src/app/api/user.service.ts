import { Observable, Subject } from "rxjs";
import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';

import { environment } from '../../environments/environment';
import { AppUser } from "../resources/app-user";
import APIROUTES from '../config/api-routes';

@Injectable({
    providedIn: 'root',
})
export class AuthenticationService {

    public static ROUTES = {
        projects: `${environment.apiPath}${APIROUTES.users}`
    };

    constructor(private http: HttpClient) {
    }

}