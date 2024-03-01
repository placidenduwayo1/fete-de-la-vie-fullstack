import { Injectable } from '@angular/core';
import { Message } from 'primeng/api';
import { Observable, Observer, Subject, of, share } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  private subject:Subject<Message>;

  constructor() { 
    this.subject = new Subject();
  }


  getObservable(){
    return this.subject.asObservable();
  }
 
  addMessage(message:Message){
    this.subject.next(message);
  }
}
