import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InterventionStructureCreateComponent } from './intervention-structure-create.component';

describe('InterventionStructureCreateComponent', () => {
  let component: InterventionStructureCreateComponent;
  let fixture: ComponentFixture<InterventionStructureCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [InterventionStructureCreateComponent]
    });
    fixture = TestBed.createComponent(InterventionStructureCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
