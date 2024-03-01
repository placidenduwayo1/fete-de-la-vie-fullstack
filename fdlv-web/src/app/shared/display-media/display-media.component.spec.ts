import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisplayMediaComponent } from './display-media.component';

describe('DisplayMediaComponent', () => {
  let component: DisplayMediaComponent;
  let fixture: ComponentFixture<DisplayMediaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [DisplayMediaComponent]
    });
    fixture = TestBed.createComponent(DisplayMediaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
