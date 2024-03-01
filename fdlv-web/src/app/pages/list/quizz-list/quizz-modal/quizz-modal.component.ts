import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable, first } from 'rxjs';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { ModalTypeEnum } from 'src/app/enums/modal-type.enum';
import { Quizz } from 'src/app/model/quizz.model';
import { EntityResponseType } from 'src/app/services/quizz.service';
import { QuizzService } from 'src/app/services/quizz.service';


import { TableModule } from 'primeng/table'
import { FormsModule } from '@angular/forms'
import { InputTextModule } from 'primeng/inputtext'
import { CardModule } from 'primeng/card'
import { ButtonModule } from 'primeng/button'
import { DialogModule } from 'primeng/dialog'
import { QuestionListComponent } from 'src/app/entities/question/question-list/question-list.component';
import { Question } from 'src/app/model/question.model';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-quizz-modal',
  standalone: true,
  imports: [CommonModule,
    TableModule,
    FormsModule,
    InputTextModule,
    CardModule,
    ButtonModule,
    DialogModule,
    QuestionListComponent, ToastModule],
    providers: [MessageService ],
  templateUrl: './quizz-modal.component.html',
  styleUrls: ['./quizz-modal.component.scss']
})
export class QuizzModalComponent {
  @ViewChild(QuestionListComponent) questionListComponent : QuestionListComponent;

  modalType: ModalTypeEnum = ModalTypeEnum.CREATE;

  data: Quizz = new Quizz();

  title: string = "Créer";

  displayModal: boolean = false;
  isLoading: boolean;



  constructor(private quizzService: QuizzService, private alertService: AlertService, 
    private route: Router, private messageService: MessageService) { }

  ngOnInit() {
  }

  getData() {
    return this.data;
  }

  save() {
    var observable: Observable<EntityResponseType>;
    var titreAlerte: string;

    this.data.questions = this.questionListComponent.dataList;

    switch (this.modalType) {
      case ModalTypeEnum.CREATE: {
        observable = this.quizzService.createDetails(this.data);
        titreAlerte = "Création";
        break;
      }
      default: {
        observable = this.quizzService.updateDetails(this.data);
        titreAlerte = "Modification";
        break;
      }
    }

    observable.pipe(first()).subscribe({
      next: response => {
        console.log(response)
        this.questionListComponent.deleteQuestions();
      //  this.alertService.addMessage({ severity: AlertSeverity.SUCCESS, summary: titreAlerte, detail: "Le quizz a été sauvegardé." });
        this.messageService.add({ severity: AlertSeverity.SUCCESS, summary: titreAlerte, detail: "Le quizz a été sauvegardé." });
    
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
        this.reloadPage()
        this.messageService.add({ severity: AlertSeverity.ERROR, summary: titreAlerte, detail: "Une erreur est survenue ! Le quizz n'a pas pu être sauvegardé." });
      }
    })
  }

  reloadPage() {
    this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.route.navigate(['quizz-list'])
    })
  }

  open(modaltype: ModalTypeEnum, data?: Quizz) {
    this.questionListComponent.clean();
    this.modalType = modaltype;

    switch (this.modalType) {
      case ModalTypeEnum.CREATE: {
        this.title = "Créer";
        this.data = new Quizz();
        this.data.questions =[];
        this.questionListComponent.dataList = this.data.questions;
        break;
      }
      case ModalTypeEnum.EDIT: {
        this.title = "Modifier";
        this.quizzService.findDetails(data.id).subscribe({
          next:(response)=>{
            this.data = response.body;
            this.questionListComponent.dataList = this.data.questions;
            this.questionListComponent.ordonner();
          },
          error:(error)=>{
            this.alertService.addMessage({ severity: AlertSeverity.ERROR, summary: "Modifcation.", detail: "Une erreur est survenue ! Le quizz est introuvable." });
        }
        });
        break;
      }
      default: {
        this.title = "Consultation";
        this.data = data;
        break;
      }
    }
    this.displayModal = true;
  }


  close() {
    this.displayModal = false;
    this.reloadPage()
  }
}
