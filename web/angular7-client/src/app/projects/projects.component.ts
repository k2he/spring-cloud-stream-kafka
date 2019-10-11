import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

import { SideNavItem } from '../shared/side-navi/side-navi.types'

@Component({
    selector: 'app-projects',
    templateUrl: './projects.component.html',
    styleUrls: ['./projects.component.scss']
})
export class ProjectsComponent implements OnInit {

    naviListItems: SideNavItem[];

    constructor(private translate: TranslateService) {
        //TODO: later can load the list from database
        this.naviListItems = [
            { name: this.translate.instant('projects-page.naviItems.all'), url: "/projects/all", active: true },
            { name: this.translate.instant('projects-page.naviItems.create'), url: "/projects/new", active: true },
            { name: this.translate.instant('projects-page.naviItems.in-progress'), url: "/projects/inprogress", active: false },
            { name: this.translate.instant('projects-page.naviItems.schedule'), url: "", active: false },
            { name: this.translate.instant('projects-page.naviItems.completed'), url: "", active: false }
        ];
    }

    ngOnInit() {
    }
}
