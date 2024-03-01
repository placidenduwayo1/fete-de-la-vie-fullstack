import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RoleStructureComponent } from './role-structure.component';


describe('roleStructureComponent', () => {
  let component: RoleStructureComponent;
  let fixture: ComponentFixture<RoleStructureComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RoleStructureComponent]
    });
    fixture = TestBed.createComponent(RoleStructureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
