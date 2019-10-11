import { TestBed, inject } from '@angular/core/testing';

import { NewProjectCountService } from './new-project-count.service';

describe('ProjectnotifyService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NewProjectCountService]
    });
  });

  it('should be created', inject([NewProjectCountService], (service: NewProjectCountService) => {
    expect(service).toBeTruthy();
  }));
});
