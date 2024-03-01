import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuizzListComponent } from './quizz-list.component';

describe('QuizzListComponent', () => {
  let component: QuizzListComponent;
  let fixture: ComponentFixture<QuizzListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [QuizzListComponent]
    });
    fixture = TestBed.createComponent(QuizzListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
