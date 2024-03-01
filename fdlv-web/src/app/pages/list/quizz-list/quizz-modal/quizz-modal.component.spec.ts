import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizzModalComponent } from './quizz-modal.component';

describe('QuizzModalComponent', () => {
  let component: QuizzModalComponent;
  let fixture: ComponentFixture<QuizzModalComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [QuizzModalComponent]
    });
    fixture = TestBed.createComponent(QuizzModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
