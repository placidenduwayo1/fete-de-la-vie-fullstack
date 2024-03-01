import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActeurFormComponent } from './acteur-form.component';

describe('ActeurFormComponent', () => {
  let component: ActeurFormComponent;
  let fixture: ComponentFixture<ActeurFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ActeurFormComponent]
    });
    fixture = TestBed.createComponent(ActeurFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
