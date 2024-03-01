import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AjoutModifFicheForumComponent } from './ajout-modif-fiche-forum.component';


describe('AjoutModifFicheForumComponent', () => {
  let component: AjoutModifFicheForumComponent;
  let fixture: ComponentFixture<AjoutModifFicheForumComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AjoutModifFicheForumComponent]
    });
    fixture = TestBed.createComponent(AjoutModifFicheForumComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
