import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';

import STORAGEKEYS from '../config/storage-keys';

@Injectable({
    providedIn: 'root'
})
export class UtilService {

    httpOptions = {
        headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    constructor() { }

    deepTrim(obj) {
        for (var prop in obj) {
            var value = obj[prop], type = typeof value;
            if (value != null && (type == "string" || type == "object") && obj.hasOwnProperty(prop)) {
                if (type == "object") {
                    this.deepTrim(obj[prop]);
                } else {
                    obj[prop] = obj[prop].trim();
                }
            }
        }
    }

    isCurrentLocalEnglish(): boolean {
        const currentLanguage = localStorage.getItem(STORAGEKEYS.LANGUAGE_CHOOSEN);
        return currentLanguage === 'en';
    }
}
