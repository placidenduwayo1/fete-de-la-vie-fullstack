import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FdlvVideoListComponentComponent } from './fdlv-video-list.component.component';

describe('FdlvVideoListComponentComponent', () => {
  let component: FdlvVideoListComponentComponent;
  let fixture: ComponentFixture<FdlvVideoListComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FdlvVideoListComponentComponent]
    });
    fixture = TestBed.createComponent(FdlvVideoListComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
