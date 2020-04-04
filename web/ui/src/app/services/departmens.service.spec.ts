import { TestBed } from '@angular/core/testing';

import { DepartmentsService } from './departments.service';

describe('DepartmensService', () => {
  let service: DepartmentsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DepartmentsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
