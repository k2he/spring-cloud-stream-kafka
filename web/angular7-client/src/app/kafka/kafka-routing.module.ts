import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { KafkaComponent } from './kafka.component';

const routes: Routes = [
  {
    path: '', component: KafkaComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class KafkaRoutingModule { }