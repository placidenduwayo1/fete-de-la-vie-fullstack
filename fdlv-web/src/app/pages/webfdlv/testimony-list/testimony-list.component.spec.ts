import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestimonyListComponent } from './testimony-list.component';

describe('TestimonyListComponent', () => {
  let component: TestimonyListComponent;
  let fixture: ComponentFixture<TestimonyListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TestimonyListComponent]
    });
    fixture = TestBed.createComponent(TestimonyListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
