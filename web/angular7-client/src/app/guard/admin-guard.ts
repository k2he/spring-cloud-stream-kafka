import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { AuthenticationService } from '../api/authentication.service';


@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {

  constructor(private router: Router,
    private authService: AuthenticationService) {
  }

  canActivate() {
    // return true;
    if (this.authService.isAuthenticated()) {
      var user = this.authService.getCurrentUser();
      if (user && user.authorities) {
        var result = user.authorities.find(auth => auth.authority.trim() === 'ROLE_ADMIN');
        return result != null;
      }
    }
    return false;
  }

}