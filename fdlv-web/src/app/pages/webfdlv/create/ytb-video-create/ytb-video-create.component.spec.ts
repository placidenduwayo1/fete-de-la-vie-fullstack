import { ComponentFixture, TestBed } from '@angular/core/testing';

import { YtbVideoCreateComponent } from './ytb-video-create.component';

describe('YtbVideoCreateComponent', () => {
  let component: YtbVideoCreateComponent;
  let fixture: ComponentFixture<YtbVideoCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [YtbVideoCreateComponent]
    });
    fixture = TestBed.createComponent(YtbVideoCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
