import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { KafkaRoutingModule } from './kafka-routing.module';
import { KafkaComponent } from './kafka.component';
import { SharedModule } from '../shared/shared.module';
import { MaterialModuleModule } from '../material-module/material-module.module';

@NgModule({
  imports: [
    SharedModule,
    MaterialModuleModule,
    ReactiveFormsModule,
    KafkaRoutingModule
  ],
  declarations: [
    KafkaComponent
  ]
})
export class KafkaModule { }
