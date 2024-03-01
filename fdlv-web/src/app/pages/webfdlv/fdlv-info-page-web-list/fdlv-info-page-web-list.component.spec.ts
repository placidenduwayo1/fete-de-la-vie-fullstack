import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FdlvInfoPageWebListComponent } from './fdlv-info-page-web-list.component';

describe('FdlvInfoPageWebListComponent', () => {
  let component: FdlvInfoPageWebListComponent;
  let fixture: ComponentFixture<FdlvInfoPageWebListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FdlvInfoPageWebListComponent]
    });
    fixture = TestBed.createComponent(FdlvInfoPageWebListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
