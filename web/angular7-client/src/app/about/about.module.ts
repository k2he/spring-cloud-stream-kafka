import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AboutRoutingModule } from './about-routing.module';
import { AboutComponent } from './about.component';
import { SharedModule } from '../shared/shared.module';
import { FrontEndComponent } from './front-end/front-end.component';
import { BackEndComponent } from './back-end/back-end.component';
import { MaterialModuleModule } from '../material-module/material-module.module';

@NgModule({
  declarations: [
    AboutComponent,
    FrontEndComponent,
    BackEndComponent
  ],
  imports: [
    SharedModule,
    MaterialModuleModule,
    AboutRoutingModule
  ]
})
export class AboutModule { }
