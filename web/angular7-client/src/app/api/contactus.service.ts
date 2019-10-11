
import { throwError, Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { UtilService } from '../services/util.service';
import { ContactInfo } from '../resources/contact';
import APIROUTES from '../config/api-routes';

@Injectable({
    providedIn: 'root',
})
export class ContactUsService {

    public static ROUTES = {
        contactus: `${environment.apiPath}${APIROUTES.contactus}`
    };

    constructor(private http: HttpClient,
        private utilService: UtilService) { }

    public getAllContactInfos(): Observable<ContactInfo[]> {
        return this.http.get<ContactInfo[]>(ContactUsService.ROUTES.contactus);
    }

    public createContactInfo(info: ContactInfo): Observable<ContactInfo> {
        return this.http.post<ContactInfo>(ContactUsService.ROUTES.contactus, info, this.utilService.httpOptions);
    }

    public getContactInfoById(todoId: number) {
        // will use this.http.get()
    }

    public updateContactInfo(info: ContactInfo) {
        // will use this.http.put()
    }

    public deleteContactInfoById(infoId: number) {
        // will use this.http.delete()
    }
}
