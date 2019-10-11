import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { TranslateService } from '@ngx-translate/core';
import { AdminComponent } from './admin.component';
import { AdminRoutingModule } from './admin-routing.module';
import { RegisterUserComponent } from './register-user/register-user.component';
import { ManageUserComponent } from './manage-user/manage-user.component';
import { MaterialModuleModule } from '../material-module/material-module.module';

@NgModule({
  imports: [
    SharedModule,
    AdminRoutingModule,
    MaterialModuleModule
  ],
  declarations: [
    AdminComponent,
    RegisterUserComponent,
    ManageUserComponent
  ]
})
export class AdminModule { }
