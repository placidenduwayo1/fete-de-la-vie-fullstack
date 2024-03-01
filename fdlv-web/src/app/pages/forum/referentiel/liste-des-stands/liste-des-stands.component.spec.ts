import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListeDesStandsComponent } from './liste-des-stands.component';

describe('ListeDesStandsComponent', () => {
  let component: ListeDesStandsComponent;
  let fixture: ComponentFixture<ListeDesStandsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ListeDesStandsComponent]
    });
    fixture = TestBed.createComponent(ListeDesStandsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
