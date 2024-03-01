import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AjoutModifBanniereComponent } from './ajout-modif-banniere.component';


describe('AjoutModifBanniereComponent', () => {
  let component: AjoutModifBanniereComponent;
  let fixture: ComponentFixture<AjoutModifBanniereComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AjoutModifBanniereComponent]
    });
    fixture = TestBed.createComponent(AjoutModifBanniereComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
