import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MobileAvisParcoursCreateComponent } from './mobile-avis-parcours-create.component';

describe('MobileAvisParcoursCreateComponent', () => {
  let component: MobileAvisParcoursCreateComponent;
  let fixture: ComponentFixture<MobileAvisParcoursCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MobileAvisParcoursCreateComponent]
    });
    fixture = TestBed.createComponent(MobileAvisParcoursCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
