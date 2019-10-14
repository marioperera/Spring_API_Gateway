import { TestBed } from '@angular/core/testing';

import { GetcurrentapisService } from './getcurrentapis.service';

describe('GetcurrentapisService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: GetcurrentapisService = TestBed.get(GetcurrentapisService);
    expect(service).toBeTruthy();
  });
});
