import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SideNaviComponent } from './side-navi.component';

describe('SideNaviComponent', () => {
  let component: SideNaviComponent;
  let fixture: ComponentFixture<SideNaviComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SideNaviComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SideNaviComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
