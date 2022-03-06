import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginReminderComponent } from './login-reminder.component';

describe('LoginReminderComponent', () => {
  let component: LoginReminderComponent;
  let fixture: ComponentFixture<LoginReminderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LoginReminderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginReminderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
