import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogoTeaserModalComponent } from './logo-teaser-modal.component';

describe('LogoTeaserModalComponent', () => {
  let component: LogoTeaserModalComponent;
  let fixture: ComponentFixture<LogoTeaserModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [LogoTeaserModalComponent]
    });
    fixture = TestBed.createComponent(LogoTeaserModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
