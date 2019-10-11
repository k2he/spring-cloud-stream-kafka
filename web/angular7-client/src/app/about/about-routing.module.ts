import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AboutComponent } from './about.component';
import { BackEndComponent } from './back-end/back-end.component';
import { FrontEndComponent } from './front-end/front-end.component';

const routes: Routes = [
  {
    path: '', 
    component: AboutComponent,
    children: [
      {
          path: '',
          redirectTo: 'backend',
          pathMatch: 'full',
      },
      {
          path: 'backend',
          component: BackEndComponent,
      },
      {
          path: 'frontend',
          component: FrontEndComponent,
      }
  ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AboutRoutingModule { }
