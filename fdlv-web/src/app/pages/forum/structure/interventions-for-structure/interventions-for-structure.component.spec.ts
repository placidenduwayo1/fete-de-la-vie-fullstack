import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InterventionsForStructureComponent } from './interventions-for-structure.component';

describe('InterventionsForStructureComponent', () => {
  let component: InterventionsForStructureComponent;
  let fixture: ComponentFixture<InterventionsForStructureComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [InterventionsForStructureComponent]
    });
    fixture = TestBed.createComponent(InterventionsForStructureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
