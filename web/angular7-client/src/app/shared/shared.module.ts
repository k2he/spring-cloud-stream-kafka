import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ErrorStateMatcher } from '@angular/material/core';
import { MaterialModuleModule } from '../material-module/material-module.module'
import { CustomCurrencyPipe } from '../shared/pipes/custom-currency.pipe';
import { CustomCurrencyFormatterDirective } from '../shared/directives/custom-currency-formatter.directive';
import { SideNaviComponent } from './side-navi/side-navi.component';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        TranslateModule,

        MaterialModuleModule
    ],
    declarations: [
        CustomCurrencyPipe,
        CustomCurrencyFormatterDirective,
        SideNaviComponent,
    ],
    exports: [
        CommonModule,
        TranslateModule,

        CustomCurrencyPipe,
        CustomCurrencyFormatterDirective,
        RouterModule,
        SideNaviComponent,
    ]

})
export class SharedModule { }
