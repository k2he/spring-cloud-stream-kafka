import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

import STORAGEKEYS from './config/storage-keys';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {


  constructor(private translate: TranslateService) {
    const selectedLanguage = localStorage.getItem(STORAGEKEYS.LANGUAGE_CHOOSEN);
    //If language already set, use current selected language, otherwise default to English.
    translate.setDefaultLang(selectedLanguage ? selectedLanguage : 'en');
  }
  
}

