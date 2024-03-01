import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Answer } from 'src/app/model/answer.model';
import { ButtonModule } from 'primeng/button';
import { FormsModule } from '@angular/forms';
import { CheckboxModule } from 'primeng/checkbox';
import { TableModule } from 'primeng/table';

@Component({
  selector: 'app-answer-list',
  standalone: true,
  imports: [CommonModule,
  ButtonModule,FormsModule,
CheckboxModule,
TableModule],
  templateUrl: './answer-list.component.html',
  styleUrls: ['./answer-list.component.scss']
})
export class AnswerListComponent {
  @Input() answers:Answer[] = [];

  removeReponse(index:number){
    this.answers.splice(index,1);
    console.log('after delete',this.answers)
  }

  add(): void {
    this.answers.push(new Answer());
  }
}
