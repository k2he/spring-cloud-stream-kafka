import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

import { SideNavItem } from '../shared/side-navi/side-navi.types'

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  naviListItems: SideNavItem[];

  constructor(private translate: TranslateService) {
    //TODO: later can load the list from database
    this.naviListItems = [
      { name: this.translate.instant('admin-page.naviItems.manage'), url: "/admin/manage", active: true },
      { name: this.translate.instant('admin-page.naviItems.register'), url: "/admin/register", active: true }
    ];
  }

  ngOnInit() {
  }

}
