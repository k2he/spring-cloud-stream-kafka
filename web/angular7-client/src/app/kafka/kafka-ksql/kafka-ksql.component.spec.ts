import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { KafkaKsqlComponent } from './kafka-ksql.component';

describe('KafkaKsqlComponent', () => {
  let component: KafkaKsqlComponent;
  let fixture: ComponentFixture<KafkaKsqlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ KafkaKsqlComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(KafkaKsqlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
