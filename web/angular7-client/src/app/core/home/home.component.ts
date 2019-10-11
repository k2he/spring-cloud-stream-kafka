import { Component, OnInit } from '@angular/core';
import { trigger, style, animate, transition } from '@angular/animations';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { environment } from '../../../environments/environment';
import { AuthenticationService } from '../../api/authentication.service';

export const OAuth2_RESPONSE: string = 'OAuth2Response';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  animations: [
    trigger('flyIn', [
      transition('void => *', [
        style({
          opacity: 0.5,
          transform: 'translateX(160%)'
        }),
        animate('900ms cubic-bezier(0.25, 0.1, 0.25, 1)')
      ])
    ])
  ]
})
export class HomeComponent implements OnInit {

  apiPath: string;
  
  constructor(private activeRoute: ActivatedRoute,
    private location: Location,
    private authService: AuthenticationService) {
    let response = this.activeRoute.snapshot.queryParamMap.get(OAuth2_RESPONSE);
    if (response) {//If contains token then save it
      authService.saveOauth2Repsonse(response);
      //Then remove token from url
      this.location.replaceState(this.location.path().split('?')[0], '');
    }
  }

  ngOnInit() {
    this.apiPath = environment.apiPath;
  }

}
