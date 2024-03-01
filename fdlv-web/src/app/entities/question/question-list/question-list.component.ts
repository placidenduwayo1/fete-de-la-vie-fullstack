import { Component, Input, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TableModule } from 'primeng/table'
import { FormsModule } from '@angular/forms'
import { InputTextModule } from 'primeng/inputtext'
import { CardModule } from 'primeng/card'
import { ButtonModule } from 'primeng/button'
import { DialogModule } from 'primeng/dialog'

import { ToastModule } from 'primeng/toast';

import { TagModule } from 'primeng/tag'
import { PanelModule } from 'primeng/panel'
import { NavbarComponent } from 'src/app/ui/navbar/navbar.component'
import { Router, RouterOutlet } from '@angular/router'
import { RippleModule } from 'primeng/ripple'
import { CheckboxModule } from 'primeng/checkbox'
import { MessageModule } from 'primeng/message'
import { IQuestion, Question } from 'src/app/model/question.model';
import { ModalTypeEnum } from 'src/app/enums/modal-type.enum';
import { QuestionService } from 'src/app/services/question.service';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { QuestionModalComponent } from '../question-modal/question-modal.component';
import { DialogDeleteComponent } from 'src/app/pages/shared/dialog-delete/dialog-delete.component';
import { OrderListModule } from 'primeng/orderlist';
import { BadgeModule } from 'primeng/badge';

@Component({
  selector: 'app-question-list',
  standalone: true,
  imports: [CommonModule,
    ToastModule,
    PanelModule,
    TableModule,
    FormsModule,
    InputTextModule,
    NavbarComponent,
    RouterOutlet,
    CardModule,
    TagModule,
    ButtonModule,
    RippleModule,
    DialogModule,
    CheckboxModule,
    MessageModule,
    QuestionModalComponent,
    DialogDeleteComponent,
    OrderListModule, BadgeModule, PanelModule],
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.scss']
})
export class QuestionListComponent {

  @Input() dataList: Question[];
  @ViewChild(QuestionModalComponent) modal!: QuestionModalComponent;
  idOrGuidToDelete: number|string;
  showConfirmationDialog: boolean;

  deleteQuestionList: Question[] = [];

  constructor(private questionService: QuestionService, private alertService: AlertService, private route: Router) { }

  ngOnInit() {
  }

  delete(idOrGuid: number|string): void {
    this.deleteQuestionList.push(this.dataList.find(question => this.getIdOrGuid(question) == idOrGuid));
    this.dataList = this.dataList.filter(question => this.getIdOrGuid(question) != idOrGuid);
    this.onOrderChange();
    this.closeConfirmationDialog();
  }

  openEditForm(order: number, id: number) {
   this.modal.open(order, ModalTypeEnum.EDIT, this.dataList.find((q: Question) => q.id == id));
  }

  openCreateForm() {
    this.modal.open(this.dataList.length+1, ModalTypeEnum.CREATE);
  }

  saveQuestion(question: Question) {
    const qEdited = question.order - 1;
    this.dataList[qEdited] = question;
   // this.dataList = [...this.dataList];
   if(question.prevOrder !== question.order){
    this.onOrderChange();
   }
  }

  addQuestion(question: Question) {
    if (this.dataList.length == 0) {
      this.dataList = [question];
    }
    else {
      this.dataList = [...this.dataList, question];
    }
    this.onOrderChange();
  }

  openConfirmationDialog(idOrGuidToDelete: number|string) {
    this.idOrGuidToDelete = idOrGuidToDelete
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }
  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }
  onOrderChange() {
    this.dataList.forEach((a, index) => {
      a.order = index + 1;
      a.prevOrder = index;
    });
    this.ordonner();
  }

  ordonner() {
    this.dataList.sort((a, b) => a.order - b.order);
  }

  clean() {
    this.deleteQuestionList = [];
  }

  deleteQuestions() {
    this.deleteQuestionList.forEach(q => {
      if (q.id !== undefined) {
        this.questionService.delete(q.id).subscribe();
      }
    });
  }

  getIdOrGuid(question:IQuestion):number|string{
    if(question.id) return question.id
    return question.guid;
  }
}