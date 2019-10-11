import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { AuthenticationService } from '../api/authentication.service';


@Injectable({
  providedIn: 'root',
})
export class ActiveAuthGuard implements CanActivate {

  constructor(private router: Router,
    private authService: AuthenticationService) {
  }

  canActivate() {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);
    }
    return this.authService.isAuthenticated();
  }

}