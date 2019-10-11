import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

import { ContactInfo } from '../resources/contact';
import { ContactUsService } from '../api/contactus.service';
import { UtilService } from '../services/util.service';
import { NotificationService } from '../services/notification.service';

@Component({
    selector: 'app-contact',
    templateUrl: './contact.component.html',
    styleUrls: ['./contact.component.scss']
})
export class ContactComponent {
    contectInfo: ContactInfo = <ContactInfo>{};
    PHONE_NUMBER_REGEX = "^\(?([0-9]{3})\)?[- ]?([0-9]{3})[- ]?([0-9]{4})$";

    @ViewChild('contactUsForm') form;

    constructor(private contactService: ContactUsService,
        private translate: TranslateService,
        private notifyService: NotificationService,
        private utilService: UtilService) { }

    onSubmit(): void {
        this.utilService.deepTrim(this.contectInfo);
        this.contactService.createContactInfo(this.contectInfo)
            .subscribe(() => {
                const message = this.translate.instant('contact-us-page.create-success-message')
                this.notifyService.showSuccess(message);
            });
    }
}
