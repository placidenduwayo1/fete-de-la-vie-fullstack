import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogoTeaserListComponent } from './logo-teaser-list.component';

describe('LogoTeaserListComponent', () => {
  let component: LogoTeaserListComponent;
  let fixture: ComponentFixture<LogoTeaserListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [LogoTeaserListComponent]
    });
    fixture = TestBed.createComponent(LogoTeaserListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
