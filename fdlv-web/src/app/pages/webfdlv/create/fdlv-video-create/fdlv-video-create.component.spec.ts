import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FdlvVideoCreateComponent } from './fdlv-video-create.component';

describe('FdlvVideoCreateComponent', () => {
  let component: FdlvVideoCreateComponent;
  let fixture: ComponentFixture<FdlvVideoCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FdlvVideoCreateComponent]
    });
    fixture = TestBed.createComponent(FdlvVideoCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
