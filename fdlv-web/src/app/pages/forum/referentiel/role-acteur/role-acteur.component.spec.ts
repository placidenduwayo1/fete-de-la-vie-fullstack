import { ComponentFixture, TestBed } from '@angular/core/testing';

import { roleActeurComponent } from './role-acteur.component';

describe('roleActeurComponent', () => {
  let component: roleActeurComponent;
  let fixture: ComponentFixture<roleActeurComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [roleActeurComponent]
    });
    fixture = TestBed.createComponent(roleActeurComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
