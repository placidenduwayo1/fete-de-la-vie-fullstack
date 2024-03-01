import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StructureFormComponent } from './structure-form.component';

describe('StructureFormComponent', () => {
  let component: StructureFormComponent;
  let fixture: ComponentFixture<StructureFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [StructureFormComponent]
    });
    fixture = TestBed.createComponent(StructureFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
