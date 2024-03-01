import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InterventionStructureComponent } from './intervention-structure.component';

describe('StructureInterventionComponent', () => {
  let component: InterventionStructureComponent;
  let fixture: ComponentFixture<InterventionStructureComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [InterventionStructureComponent]
    });
    fixture = TestBed.createComponent(InterventionStructureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
