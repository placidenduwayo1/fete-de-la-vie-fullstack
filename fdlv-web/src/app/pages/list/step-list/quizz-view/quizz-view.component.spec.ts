import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizzViewComponent } from './quizz-view.component';

describe('QuizzViewComponent', () => {
  let component: QuizzViewComponent;
  let fixture: ComponentFixture<QuizzViewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [QuizzViewComponent]
    });
    fixture = TestBed.createComponent(QuizzViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
