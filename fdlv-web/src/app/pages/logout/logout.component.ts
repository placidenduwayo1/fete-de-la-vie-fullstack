import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router} from "@angular/router";
import { AuthService } from 'src/app/core/auth/auth.service';

@Component({
  selector: 'app-logout',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.scss']
})
export class LogoutComponent implements OnInit {

  constructor(private route:Router, private authService:AuthService) {
  }

  ngOnInit() {
console.log('logout');
    this.authService.logout();

    this.route.navigate(['/']);


  }

}
