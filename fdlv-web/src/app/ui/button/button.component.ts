import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonModule } from "primeng/button";

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [
    CommonModule,
    ButtonModule
  ],
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss']
})
export class ButtonComponent {

}
