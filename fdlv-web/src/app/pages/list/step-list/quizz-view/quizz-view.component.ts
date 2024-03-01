import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { IQuizz } from 'src/app/model/quizz.model';
import { CardModule } from 'primeng/card';
import { SharedModule } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { QuizzService } from 'src/app/services/quizz.service';
import { PanelModule } from 'primeng/panel';
import { BadgeModule } from 'primeng/badge'
import { OrderListModule } from 'primeng/orderlist';

@Component({
  selector: 'app-quizz-view',
  standalone: true,
  imports: [CommonModule, SharedModule, CardModule, ButtonModule, PanelModule, BadgeModule, OrderListModule ],
  templateUrl: './quizz-view.component.html',
  styleUrls: ['./quizz-view.component.scss']
})
export class QuizzViewComponent implements OnInit {
  quizz: IQuizz | null = null;

  constructor(protected router: Router, private quizzService: QuizzService) {}

  ngOnInit(): void {
    if(window.history.state.type== 'view'){ 
      const quizzId = Number(window.history.state.data);
      this.quizzService.findDetails(quizzId).subscribe(res =>{
        res.body.questions.sort(this.compare_order);
        this.quizz = res.body;
      })
    }
  }

  compare_order(a: any, b: any): number {
    if (a.order < b.order) {
      return -1;
    }
    if (a.order > b.order) {
      return 1;
    }
    return 0;
  }

  previousState(): void {
    window.history.back();
  }
}
