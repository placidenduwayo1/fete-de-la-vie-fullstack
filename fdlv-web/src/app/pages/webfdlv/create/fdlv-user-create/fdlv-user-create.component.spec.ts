import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FdlvUserCreateComponent } from './fdlv-user-create.component';

describe('FdlvUserCreateComponent', () => {
  let component: FdlvUserCreateComponent;
  let fixture: ComponentFixture<FdlvUserCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FdlvUserCreateComponent]
    });
    fixture = TestBed.createComponent(FdlvUserCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
