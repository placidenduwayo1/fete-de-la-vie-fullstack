import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FdlvPartenaireListComponent } from './fdlv-partenaire-list.component';

describe('FdlvPartenaireListComponent', () => {
  let component: FdlvPartenaireListComponent;
  let fixture: ComponentFixture<FdlvPartenaireListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FdlvPartenaireListComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FdlvPartenaireListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
