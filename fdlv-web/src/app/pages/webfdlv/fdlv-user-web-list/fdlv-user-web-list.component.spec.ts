import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FdlvUserWebListComponent } from './fdlv-user-web-list.component';

describe('FdlvUserWebListComponent', () => {
  let component: FdlvUserWebListComponent;
  let fixture: ComponentFixture<FdlvUserWebListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FdlvUserWebListComponent]
    });
    fixture = TestBed.createComponent(FdlvUserWebListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
