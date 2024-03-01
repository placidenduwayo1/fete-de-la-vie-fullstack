import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Message } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { AlertService } from './alert.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-alert',
  standalone: true,
  imports: [CommonModule,MessagesModule],
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss']
})
export class AlertComponent {
  messages: Message[] | undefined;
  messageListener: Subscription;

  constructor(alertService:AlertService){
    this.messageListener = alertService.getObservable().subscribe(
      (message:Message) =>{ 
        this.addMessage(message);
      }
      );
  }

  ngOnInit() {
  }

  addMessage(message:Message){
    this.messages = [message];
  }
  
}
