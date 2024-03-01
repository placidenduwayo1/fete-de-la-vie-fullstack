import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AideComponent } from './aide.component';

describe('AideComponent', () => {
  let component: AideComponent;
  let fixture: ComponentFixture<AideComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AideComponent]
    });
    fixture = TestBed.createComponent(AideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
