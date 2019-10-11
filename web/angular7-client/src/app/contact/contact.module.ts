import { NgModule } from '@angular/core';
import { MaterialModuleModule } from '../material-module/material-module.module'
import { FormsModule } from '@angular/forms';

import { ContactRoutingModule } from './contact-routing.module';
import { SharedModule } from '../shared/shared.module';
import { ContactComponent } from "./contact.component";
import { ContactUsService } from '../api/contactus.service';

@NgModule({
  imports: [
    SharedModule,
    FormsModule,
    MaterialModuleModule,
    ContactRoutingModule,
  ],
  declarations: [
    ContactComponent
  ],
  providers: [
    ContactUsService
  ]
})
export class ContactModule { }
