import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SecteurStandComponent } from './secteur-stand.component';

describe('SecteurStandComponent', () => {
  let component: SecteurStandComponent;
  let fixture: ComponentFixture<SecteurStandComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SecteurStandComponent]
    });
    fixture = TestBed.createComponent(SecteurStandComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
