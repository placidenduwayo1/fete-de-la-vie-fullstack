import { TestBed } from '@angular/core/testing';

import { LogoTeaserService } from './logo-teaser.service';

describe('LogoTeaserService', () => {
  let service: LogoTeaserService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LogoTeaserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
