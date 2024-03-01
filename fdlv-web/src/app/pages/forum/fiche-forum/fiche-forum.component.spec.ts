import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FicheForumComponent } from './fiche-forum.component';


describe('FicheForumComponent', () => {
  let component: FicheForumComponent;
  let fixture: ComponentFixture<FicheForumComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FicheForumComponent]
    });
    fixture = TestBed.createComponent(FicheForumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
