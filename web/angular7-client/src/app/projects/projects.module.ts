import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { ProjectsRoutingModule } from './projects-routing.module';
import { SharedModule } from '../shared/shared.module';
import { MaterialModuleModule } from '../material-module/material-module.module';
import { AllProjectsComponent } from './all-projects/all-projects.component';
import { NewProjectComponent } from './new-project/new-project.component';
import { InProgressProjectsComponent } from './in-progress-projects/in-progress-projects.component';
import { ProjectsComponent } from "./projects.component";

import { CustomCurrencyPipe } from '../shared/pipes/custom-currency.pipe';

@NgModule({
      imports: [
            ReactiveFormsModule,
            SharedModule,
            MaterialModuleModule,
            ProjectsRoutingModule,
      ],
      declarations: [
            ProjectsComponent,
            AllProjectsComponent,
            NewProjectComponent,
            InProgressProjectsComponent
      ],
      providers: [
            CustomCurrencyPipe
      ]
})
export class ProjectsModule { }
