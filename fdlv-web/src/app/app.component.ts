import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './ui/navbar/navbar.component';
import { HeaderComponent } from './layout/header/header.component';
import { AuthService } from './core/auth/auth.service';
import { AlertComponent } from './shared/alert/alert.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, NavbarComponent, HeaderComponent,AlertComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'fdlv-web';

  constructor(private authservice: AuthService) {
  }
  show() {
    return this.authservice.islogIn();
  }
  
}
