import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StepCreateComponent } from './step-create.component';

describe('StepCreateComponent', () => {
  let component: StepCreateComponent;
  let fixture: ComponentFixture<StepCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [StepCreateComponent]
    });
    fixture = TestBed.createComponent(StepCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
