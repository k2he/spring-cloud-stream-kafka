import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AllProjectsComponent } from './all-projects/all-projects.component';
import { NewProjectComponent } from "./new-project/new-project.component";
import { InProgressProjectsComponent } from "./in-progress-projects/in-progress-projects.component";
import { ProjectsComponent } from "./projects.component";

const routes: Routes = [
    {
        path: '',
        component: ProjectsComponent,
        children: [
            {
                path: '',
                redirectTo: 'all',
                pathMatch: 'full',
            },
            {
                path: 'all',
                component: AllProjectsComponent,
            },
            {
                path: 'edit/:id',
                component: NewProjectComponent,
            },
            {
                path: 'new',
                component: NewProjectComponent,
            },
            {
                path: 'inprogress',
                component: InProgressProjectsComponent,
            }

        ]
    }
];


@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ProjectsRoutingModule { }
