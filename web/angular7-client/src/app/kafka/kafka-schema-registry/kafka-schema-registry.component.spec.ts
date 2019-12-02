import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KafkaSchemaRegistryComponent } from './kafka-schema-registry.component';

describe('KafkaSchemaRegistryComponent', () => {
  let component: KafkaSchemaRegistryComponent;
  let fixture: ComponentFixture<KafkaSchemaRegistryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KafkaSchemaRegistryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KafkaSchemaRegistryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
