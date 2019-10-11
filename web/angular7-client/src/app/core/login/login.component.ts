import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { trigger, style, animate, transition } from '@angular/animations';

import { AuthenticationService } from '../../api/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  animations: [
    trigger('showUp', [
      transition('void => *', [
        style({
          opacity: 0.3
        }),
        animate('200ms')
      ])
    ])
  ]
})
export class LoginComponent {

  username: string;
  password: string;
  loginFailErrorMessage: string;

  constructor(private router: Router,
    private authService: AuthenticationService) { }

  clearError() {
    this.loginFailErrorMessage = "";
  }

  login(): void {
    this.clearError();

    this.authService.login(this.username, this.password)
      .subscribe(() => {
        this.router.navigate(['/home']);
      }, e => this.handleError(e));
  }

  socialLogin(loginType: string) {
    this.authService.socialLogin(loginType);
  }

  //Need figure out how to get correct error so we can highlight corrsponding input box.
  private handleError(error) {
    switch (error.status) {
      case 401:
        this.loginFailErrorMessage = "Login failed. Please make sure username and password is correct.";
    }
  }

}
