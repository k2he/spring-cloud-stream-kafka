import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminComponent } from "./admin.component";
import { RegisterUserComponent } from './register-user/register-user.component';
import { ManageUserComponent } from './manage-user/manage-user.component';


const routes: Routes = [
    {
        path: '',
        component: AdminComponent,
        children: [
            {
                path: '',
                redirectTo: 'manage',
                pathMatch: 'full',
            },
            {
                path: 'manage',
                component: ManageUserComponent,
            },
            {
                path: 'register',
                component: RegisterUserComponent,
            }
        ]
    }
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AdminRoutingModule { }
