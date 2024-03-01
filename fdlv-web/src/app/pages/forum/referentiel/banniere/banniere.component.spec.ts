import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForumThemeComponent } from './forum-theme.component';

describe('ThemeForumComponent', () => {
  let component: ForumThemeComponent;
  let fixture: ComponentFixture<ForumThemeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ForumThemeComponent]
    });
    fixture = TestBed.createComponent(ForumThemeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
