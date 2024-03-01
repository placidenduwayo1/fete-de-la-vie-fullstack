import { TestBed } from '@angular/core/testing';

import { FdlvUsersService } from './fdlv-users.service';

describe('FdlvUsersService', () => {
  let service: FdlvUsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FdlvUsersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
