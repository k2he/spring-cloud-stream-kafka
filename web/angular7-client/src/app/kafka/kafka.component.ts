import { Component, OnInit } from '@angular/core';

import { SideNavItem } from '../shared/side-navi/side-navi.types';

@Component({
  selector: 'app-kafka',
  templateUrl: './kafka.component.html',
  styleUrls: ['./kafka.component.scss']
})
export class KafkaComponent implements OnInit {

  naviListItems: SideNavItem[];

  constructor() {
    //TODO: later can load the list from database
    this.naviListItems = [
        { name: "Kafka Stream", url: "/kafka/kafka-stream", active: true },
        { name: "Kafka Connect", url: "/kafka/kafka-connect", active: true },
        { name: "Kafka KSQL", url: "/kafka/kafka-ksql", active: true },
        { name: "Kafka Schema Registry", url: "/kafka/kafka-schema-registry", active: true },
    ];
  }

  ngOnInit() {
  }
}
