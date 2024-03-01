import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MobileQfinParcoursListComponent } from './mobile-qfin-parcours-list.component';

describe('MobileQfinParcoursListComponent', () => {
  let component: MobileQfinParcoursListComponent;
  let fixture: ComponentFixture<MobileQfinParcoursListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [MobileQfinParcoursListComponent]
    });
    fixture = TestBed.createComponent(MobileQfinParcoursListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
