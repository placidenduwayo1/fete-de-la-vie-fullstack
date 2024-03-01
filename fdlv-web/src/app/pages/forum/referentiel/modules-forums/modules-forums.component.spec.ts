import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModulesForumsComponent } from './modules-forums.component';

describe('ModulesForumsComponent', () => {
  let component: ModulesForumsComponent;
  let fixture: ComponentFixture<ModulesForumsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ModulesForumsComponent]
    });
    fixture = TestBed.createComponent(ModulesForumsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
