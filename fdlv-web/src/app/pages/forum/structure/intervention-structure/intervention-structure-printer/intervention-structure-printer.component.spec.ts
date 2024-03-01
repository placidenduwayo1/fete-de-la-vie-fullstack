import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InterventionStructurePrinterComponent } from './intervention-structure-printer.component';

describe('InterventionStructurePrinterComponent', () => {
  let component: InterventionStructurePrinterComponent;
  let fixture: ComponentFixture<InterventionStructurePrinterComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [InterventionStructurePrinterComponent]
    });
    fixture = TestBed.createComponent(InterventionStructurePrinterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
