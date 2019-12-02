import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { KafkaComponent } from './kafka.component';
import { KafkaStreamComponent } from './kafka-stream/kafka-stream.component';
import { KafkaConnectComponent } from './kafka-connect/kafka-connect.component';
import { KafkaKsqlComponent } from './kafka-ksql/kafka-ksql.component';
import { KafkaSchemaRegistryComponent } from './kafka-schema-registry/kafka-schema-registry.component';

const routes: Routes = [
  {
      path: '',
      component: KafkaComponent,
      children: [
          {
              path: '',
              redirectTo: 'kafka-stream',
              pathMatch: 'full',
          },
          {
              path: 'kafka-stream',
              component: KafkaStreamComponent,
          },
          {
              path: 'kafka-connect',
              component: KafkaConnectComponent,
          },
          {
            path: 'kafka-ksql',
            component: KafkaKsqlComponent,
          },
          {
            path: 'kafka-schema-registry',
            component: KafkaSchemaRegistryComponent,
          }
      ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class KafkaRoutingModule { }
