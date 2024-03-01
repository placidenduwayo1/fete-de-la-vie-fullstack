import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InterventionStructureUpdateComponent } from './intervention-structure-update.component';

describe('InterventionStructureUpdateComponent', () => {
  let component: InterventionStructureUpdateComponent;
  let fixture: ComponentFixture<InterventionStructureUpdateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [InterventionStructureUpdateComponent]
    });
    fixture = TestBed.createComponent(InterventionStructureUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
