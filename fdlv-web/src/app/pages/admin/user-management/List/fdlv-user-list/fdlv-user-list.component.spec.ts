import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FdlvUserListComponent } from './fdlv-user-list.component';

describe('FdlvUserListComponent', () => {
  let component: FdlvUserListComponent;
  let fixture: ComponentFixture<FdlvUserListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FdlvUserListComponent]
    });
    fixture = TestBed.createComponent(FdlvUserListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
