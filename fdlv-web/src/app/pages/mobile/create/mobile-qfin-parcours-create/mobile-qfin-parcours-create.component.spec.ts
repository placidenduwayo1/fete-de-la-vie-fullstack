import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MobileQfinParcoursCreateComponent } from './mobile-qfin-parcours-create.component';

describe('MobileQfinParcoursCreateComponent', () => {
  let component: MobileQfinParcoursCreateComponent;
  let fixture: ComponentFixture<MobileQfinParcoursCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MobileQfinParcoursCreateComponent]
    });
    fixture = TestBed.createComponent(MobileQfinParcoursCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
