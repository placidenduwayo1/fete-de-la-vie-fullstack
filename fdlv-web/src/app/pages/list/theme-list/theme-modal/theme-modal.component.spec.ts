import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ThemeModalComponent } from './theme-modal.component';

describe('ThemeModalComponent', () => {
  let component: ThemeModalComponent;
  let fixture: ComponentFixture<ThemeModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ThemeModalComponent]
    });
    fixture = TestBed.createComponent(ThemeModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
