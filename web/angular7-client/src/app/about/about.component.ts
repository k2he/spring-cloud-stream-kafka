import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

import { SideNavItem } from '../shared/side-navi/side-navi.types'

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AboutComponent implements OnInit {

  naviListItems: SideNavItem[];

  constructor(private translate: TranslateService) {
    //TODO: later can load the list from database
    this.naviListItems = [
      { name: this.translate.instant('about-page.naviItems.backend'), url: "/about/backend", active: true },
      { name: this.translate.instant('about-page.naviItems.frontend'), url: "/about/frontend", active: true },
    ];
  }
  ngOnInit() {
  }

}
