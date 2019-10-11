import { Observable, Subject } from "rxjs";
import { Injectable } from "@angular/core";
import { HttpClient } from '@angular/common/http';
import { tap } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { AppUser } from '../resources/app-user';
import { AuthResponse } from '../resources/auth-response';
import STORAGEKEYS from '../config/storage-keys';
import APIROUTES from '../config/api-routes';

export class LoginAction {
}
export class LogoutAction {
}
export type AuthenticationEvent = LoginAction | LogoutAction;


@Injectable({
    providedIn: 'root',
})
export class AuthenticationService {

    private authEvents: Subject<AuthenticationEvent>;
    public static ROUTES = {
        login: `${environment.apiPath}${APIROUTES.login}`
    };

    constructor(private http: HttpClient) {
        this.authEvents = new Subject<AuthenticationEvent>();
    }

    socialLogin(loginType: string) {
        let ssoUrl = `${environment.DEFAULT_AUTH_URL}`;
        if (loginType === "google") {
            ssoUrl = `${environment.GOOGLE_AUTH_URL}`;
        } else if (loginType === "facebook") {
            ssoUrl = `${environment.FACEBOOK_AUTH_URL}`;
        } else if (loginType === "github") {
            ssoUrl = `${environment.GITHUB_AUTH_URL}`;
        }
        window.location.href = ssoUrl;
    }

    login(username: string, password: string) {
        const body = {
            username: username,
            password: password,
        }
        return this.http.post<AuthResponse>(AuthenticationService.ROUTES.login, body).pipe(tap(response => {
            this.setAuthInfo(response.token, response.user);
            this.triggerLogedinAction();
        }));
    }

    triggerLogedinAction() {
        this.authEvents.next(new LoginAction());
    }

    logout(): void {
        localStorage.removeItem(STORAGEKEYS.JWT_TOKEN);
        localStorage.removeItem(STORAGEKEYS.CURRENT_USER);
        localStorage.removeItem(STORAGEKEYS.LANGUAGE_CHOOSEN);
        this.authEvents.next(new LogoutAction());
    }

    isAuthenticated(): boolean {
        // console.log("isAuthenticated() called with value:" + localStorage.getItem('jwtToken') );
        return localStorage.getItem(STORAGEKEYS.JWT_TOKEN) != null;
    }

    saveOauth2Repsonse(responseString: string) {
        let response: AuthResponse = JSON.parse(responseString);
        if (response) {
            this.setAuthInfo(response.token, response.user);
        }
        this.triggerLogedinAction();
    }

    setAuthInfo(token: string, user: AppUser) {
        localStorage.setItem(STORAGEKEYS.JWT_TOKEN, token);
        localStorage.setItem(STORAGEKEYS.CURRENT_USER, JSON.stringify(user));
    }

    getAuthToken(): String {
        return localStorage.getItem(STORAGEKEYS.JWT_TOKEN);
    }

    getCurrentUser(): AppUser {
        return JSON.parse(localStorage.getItem(STORAGEKEYS.CURRENT_USER));
    }

    get events(): Observable<AuthenticationEvent> {
        return this.authEvents;
    }
}