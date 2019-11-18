import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KafkaStreamComponent } from './kafka-stream.component';

describe('KafkaStreamComponent', () => {
  let component: KafkaStreamComponent;
  let fixture: ComponentFixture<KafkaStreamComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KafkaStreamComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KafkaStreamComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
