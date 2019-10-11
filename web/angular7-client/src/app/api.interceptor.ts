import { Injectable, Injector } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError, of } from 'rxjs';
import { catchError, retry, tap } from 'rxjs/operators';
import { Router } from "@angular/router";

import { AuthenticationService } from './api/authentication.service';
import { SpinnerService } from './services/spinner.service';
import STORAGEKEYS from './config/storage-keys';

@Injectable()
export class ApiInterceptor implements HttpInterceptor {

    constructor(private injector: Injector,
        private router: Router,
        private spinnerService: SpinnerService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // Show Spinner
        this.spinnerService.show();

        // add authorization header with jwt token if available
        const authService = this.injector.get(AuthenticationService);
        let token = authService.getAuthToken();
        if (token) {
            request = request.clone({
                headers: request.headers.set('Authorization', 'Bearer ' + token)
            });
        }

        // Handler API call failure
        return next.handle(request).pipe(
            tap((event: HttpEvent<any>) => {
                if (event instanceof HttpResponse) {
                    this.spinnerService.hide(); // Hide Spinner
                }
                return event;
            }),
            catchError((error, caught) => {
                //intercept the respons error and displace it to the console
                console.log(error);
                this.spinnerService.hide(); // Hide Spinner
                this.handleAuthError(error);
                return of(error);
            }) as any);
    }


    private handleAuthError(error: HttpErrorResponse): Observable<any> {
        //handle your auth error or rethrow
        if (error.status === 401 && !this.isLoginError(error)) { //Login already has logic to handle failure, hence we don't want to handle it here.
            // Delete Invalid Token and User Information
            localStorage.removeItem(STORAGEKEYS.JWT_TOKEN);
            localStorage.removeItem(STORAGEKEYS.CURRENT_USER);
            console.log('Token Expired. Handled error ' + error.status);
            this.router.navigate([`/login`]);

            //Since we already handled it, we don't want error message show up on screen.
            return of(error.message);
        }
        throw error;
    }

    private isLoginError(error: HttpErrorResponse) {
        const url = error.url;
        return url.includes('/login');
    }
}