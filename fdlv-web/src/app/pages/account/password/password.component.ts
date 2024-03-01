import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable, first, of } from 'rxjs';
import { Account } from 'src/app/core/auth/account.model';

import { FormBuilder, FormsModule, NgForm, ReactiveFormsModule, Validators } from '@angular/forms';
import { CardModule } from "primeng/card";
import { AccountService } from '../account.service';
import { PasswordStrengthBarComponent } from './password-strength-bar/password-strength-bar.component';
import { PasswordService } from './password.service';
import { ButtonModule } from 'primeng/button';
import { MessagesModule } from 'primeng/messages';
import { InputTextModule } from 'primeng/inputtext';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';

@Component({
  selector: 'app-password',
  standalone: true,
  imports: [InputTextModule, CommonModule, FormsModule, CardModule, ButtonModule, ReactiveFormsModule, PasswordStrengthBarComponent, MessagesModule],
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.scss']
})
export class PasswordComponent {
  doNotMatch = false;
  error = false;
  success = false;
  account$?: Observable<Account | null>;
  passwordForm = this.fb.group({
    currentPassword: ['', [Validators.required]],
    newPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
    confirmPassword: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(50)]],
  });
  constructor(private accountService: AccountService,private alertService:AlertService, private fb: FormBuilder, private passwordService: PasswordService) { }

  ngOnInit(): void {
    this.accountService.getAccount().pipe(first()).subscribe({
      next: (response: Account) => this.account$ = of(response)
    });
  }


  changePassword(): void {
    this.error = false;
    this.success = false;
    this.doNotMatch = false;

    const newPassword = this.passwordForm.get(['newPassword'])!.value;
    if (newPassword !== this.passwordForm.get(['confirmPassword'])!.value) {
      this.doNotMatch = true;
    } else {
      this.passwordService.save(newPassword, this.passwordForm.get(['currentPassword'])!.value).pipe(first()).subscribe(
        {
          next: () => {
            this.success = true;
            this.alertService.addMessage({ severity: AlertSeverity.SUCCESS, summary: 'Mot de passe', detail: 'Changement effectué' });
          },
          error: () => {
            this.error = true;
            this.alertService.addMessage({ severity: AlertSeverity.ERROR, summary: 'Mot de passe', detail: "Une erreur est survenue ! Le mot de passe n'a pas pu être modifié." });
          }
        }
      );
    }
  }
}
