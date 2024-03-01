import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MobileUserCreateComponent } from './mobile-user-create.component';

describe('MobileUserCreateComponent', () => {
  let component: MobileUserCreateComponent;
  let fixture: ComponentFixture<MobileUserCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MobileUserCreateComponent]
    });
    fixture = TestBed.createComponent(MobileUserCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
