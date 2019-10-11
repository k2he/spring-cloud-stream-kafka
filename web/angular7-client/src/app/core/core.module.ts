import { ModuleWithProviders, NgModule, Optional, SkipSelf } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from "@angular/common";
import { RouterModule } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MaterialModuleModule } from '../material-module/material-module.module'
import { AuthenticationService } from '../api/authentication.service';
import { NewProjectCountService } from '../services/new-project-count.service';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SpinnerComponent } from './spinner/spinner.component';
import { DialogComponent } from '../dialog/dialog.component';

@NgModule({
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    RouterModule,
    FormsModule,
    TranslateModule,
    MaterialModuleModule,
  ],
  declarations: [
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    PageNotFoundComponent,
    LoginComponent,
    SpinnerComponent,
    DialogComponent
  ],
  exports: [
    HeaderComponent,
    FooterComponent,
    SpinnerComponent
  ],
  entryComponents: [DialogComponent]
})

export class CoreModule {

  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error(
        'CoreModule is already loaded. Import it in the AppModule only');
    }
  }
}
