import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MessagesModule } from 'primeng/messages';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { DividerModule } from 'primeng/divider';
import { FormsModule } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { AuthService } from 'src/app/core/auth/auth.service';
import { MessageModule } from 'primeng/message';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    PasswordModule,
    DividerModule,
    FormsModule,
    InputTextModule,
    ProgressSpinnerModule,
    ToastModule,
    CardModule,
    ButtonModule,
    MessagesModule,
    MessageModule,
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  username = signal<string>('');
  password = signal<string>('');
  messageerro = false;
  token: any;

  constructor(private service: AuthService, private router: Router) {}
  Login(user: any) {
    let data = user.value;
    this.username.set(data.username);
    this.password.set(data.password);
    this.service.login(data).subscribe({
      next: (response) => {
        console.log(response);

        this.token = response;
        this.service.saveToken(this.token.id_token);
        this.service.getIdentity(this.token.id_token);

        this.router.navigate(['/home']);
      },
      error: () => {
        this.messageerro = true;
      }
  });
  }
}
