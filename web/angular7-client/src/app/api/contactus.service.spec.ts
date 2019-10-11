import { TestBed, inject } from '@angular/core/testing';

import { ContactUsService } from './contactus.service';

describe('ApiService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ContactUsService]
    });
  });

  it('should be created', inject([ContactUsService], (service: ContactUsService) => {
    expect(service).toBeTruthy();
  }));
});
