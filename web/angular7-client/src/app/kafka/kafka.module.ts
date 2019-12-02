import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';

import { KafkaRoutingModule } from './kafka-routing.module';
import { KafkaComponent } from './kafka.component';
import { SharedModule } from '../shared/shared.module';
import { MaterialModuleModule } from '../material-module/material-module.module';
import { KafkaStreamComponent } from './kafka-stream/kafka-stream.component';
import { KafkaConnectComponent } from './kafka-connect/kafka-connect.component';
import { KafkaKsqlComponent } from './kafka-ksql/kafka-ksql.component';
import { KafkaSchemaRegistryComponent } from './kafka-schema-registry/kafka-schema-registry.component';

@NgModule({
  imports: [
    SharedModule,
    MaterialModuleModule,
    ReactiveFormsModule,
    KafkaRoutingModule,
  ],
  declarations: [
    KafkaComponent,
    KafkaStreamComponent,
    KafkaConnectComponent,
    KafkaKsqlComponent,
    KafkaSchemaRegistryComponent
  ]
})
export class KafkaModule { }
