import { Component, OnInit, OnDestroy } from '@angular/core';
import { trigger, style, animate, transition } from '@angular/animations';
import { Router } from "@angular/router";
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { TranslateService } from '@ngx-translate/core';
import { NewProjectCountService } from '../../services/new-project-count.service';
import { AuthenticationService } from '../../api/authentication.service';
import { AdminGuard } from '../../guard/admin-guard';
import { AppUser } from '../../resources/app-user';
import { UtilService } from '../../services/util.service';
import STORAGEKEYS from '../../config/storage-keys';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  animations: [
    trigger('showUp', [
      transition('void => *', [
        style({
          opacity: 0.2
        }),
        animate('900ms')
      ])
    ])
  ]
})
export class HeaderComponent implements OnInit, OnDestroy {

  private unsubscribe$ = new Subject<void>();

  newProjectNum = 0;
  isLoggedIn: boolean;
  user: AppUser;
  adminGuard: AdminGuard;
  currentLanguage: string;

  constructor(private authService: AuthenticationService,
    private router: Router,
    private guard: AdminGuard,
    private translate: TranslateService,
    private utilService: UtilService,
    private projectService: NewProjectCountService) {
    this.adminGuard = guard;
    this.currentLanguage = localStorage.getItem(STORAGEKEYS.LANGUAGE_CHOOSEN);
  }

  ngOnInit() {
    this.projectService.events$.pipe(
      takeUntil(this.unsubscribe$)  // Unsubscribe projectService.events$ on unsubscribe$ emission.
    ).subscribe(result => { this.newProjectNum++; });
    
    this.loadData();

    this.authService.events.subscribe(() => {
      this.loadData();
    });
  }

  loadData() {
    this.isLoggedIn = this.authService.isAuthenticated();
    this.user = this.authService.getCurrentUser();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/home']);
  }

  changeLanguage() {
    if (this.isCurrentLanEnglish()) {
      this.currentLanguage = 'fr';
    } else {
      this.currentLanguage = 'en';
    }
    //Set Choosen Language
    localStorage.setItem(STORAGEKEYS.LANGUAGE_CHOOSEN, this.currentLanguage);
    this.translate.use(this.currentLanguage);
  }

  isCurrentLanEnglish(): boolean {
    return this.utilService.isCurrentLocalEnglish();
  }

  ngOnDestroy() {
    /* We don't have to do this one, because this observable is Application level. But we do here 
    *  to demostract how to properly do unsubscribe.
    *  Notes: 
    *  1) HttpClient Observable doesn't need do unsubscribe.
    *  2) Some components (eg AppComponent) and most of the services (with exception of services from lazy loaded modules and services provided in @Component decorator) in our Angular application will be instantiated only once during the application startup.
    *     So it's ok to subscribe to an Observable without providing any unsubscription logic
    *  3) All other cases, we should unsubscribe to avoid memory leak.
    */
    this.unsubscribe$.next();
    this.unsubscribe$.unsubscribe();
  }
}
