import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FdlvInfoPageWebCreateComponent } from './fdlv-info-page-web-create.component';

describe('FdlvInfoPageWebCreateComponent', () => {
  let component: FdlvInfoPageWebCreateComponent;
  let fixture: ComponentFixture<FdlvInfoPageWebCreateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FdlvInfoPageWebCreateComponent]
    });
    fixture = TestBed.createComponent(FdlvInfoPageWebCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
