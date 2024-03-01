import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FdlvPartenaireCreateComponent } from './fdlv-partenaire-create.component';

describe('FdlvPartenaireCreateComponent', () => {
  let component: FdlvPartenaireCreateComponent;
  let fixture: ComponentFixture<FdlvPartenaireCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FdlvPartenaireCreateComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FdlvPartenaireCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
