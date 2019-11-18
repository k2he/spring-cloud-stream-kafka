import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KafkaConnectComponent } from './kafka-connect.component';

describe('KafkaConnectComponent', () => {
  let component: KafkaConnectComponent;
  let fixture: ComponentFixture<KafkaConnectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KafkaConnectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KafkaConnectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
