import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MobileAvisParcoursListComponent } from './mobile-avis-parcours-list.component';

describe('MobileAvisParcoursListComponent', () => {
  let component: MobileAvisParcoursListComponent;
  let fixture: ComponentFixture<MobileAvisParcoursListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MobileAvisParcoursListComponent]
    });
    fixture = TestBed.createComponent(MobileAvisParcoursListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
