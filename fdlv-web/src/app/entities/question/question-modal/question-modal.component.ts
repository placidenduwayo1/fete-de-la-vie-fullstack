import { Component, EventEmitter, Host, Output, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { Router } from '@angular/router';
import { ModalTypeEnum } from 'src/app/enums/modal-type.enum';
import { IQuestion, Question, } from 'src/app/model/question.model';

import { TableModule } from 'primeng/table'
import { FormsModule } from '@angular/forms'
import { InputTextModule } from 'primeng/inputtext'
import { CardModule } from 'primeng/card'
import { ButtonModule } from 'primeng/button'
import { DialogModule } from 'primeng/dialog'
import { AnswerListComponent } from 'src/app/entities/answer/answer-list/answer-list.component';
import { QuestionService } from 'src/app/services/question.service';
import { ResponseType } from 'src/app/model/response-type.model';
import { Guid } from 'guid-typescript';
import { SelectButtonModule } from 'primeng/selectbutton';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-question-modal',
  standalone: true,
  imports: [CommonModule,
    TableModule,
    FormsModule,
    InputTextModule,
    CardModule,
    ButtonModule,
    DialogModule,
  AnswerListComponent,
  SelectButtonModule, ToastModule],
  providers: [MessageService ],
  templateUrl: './question-modal.component.html',
  styleUrls: ['./question-modal.component.scss']
})
export class QuestionModalComponent {
  @Output() saveQuestion = new EventEmitter<Question>()
  @Output() addQuestion = new EventEmitter<Question>()
  @ViewChild(AnswerListComponent) answersComponent! :AnswerListComponent;

  modalType:ModalTypeEnum=ModalTypeEnum.CREATE;

  data:Question = new Question();

  title:string = "Créer";

  displayModal:boolean=false;
  isLoading: boolean;
  typeQuestionnaires =[{value:"UNIQUE", label:'Choix unique'},{value:"MULTIPLE" , label:'Choix multiple'}]
   


  constructor() {

   }

  ngOnInit(){
  }

  save(){
    this.data.answers=this.answersComponent.answers;
    this.data.text = this.data.label;
    if(this.modalType == ModalTypeEnum.CREATE) {
      this.addQuestion.emit(this.data);
    }

    if(this.modalType == ModalTypeEnum.EDIT) {
      this.saveQuestion.emit(this.data);
    }

    this.close();
  }


  open(order:number,modaltype:ModalTypeEnum, data?:Question){
    this.modalType =modaltype;

    switch(this.modalType){
      case ModalTypeEnum.CREATE: { 
       this.title="Créer";
       this.data = new Question();
       this.data.order = order;
       this.data.answers = [];
       this.data.guid = Guid.create().toString();
         break; 
      } 
      case ModalTypeEnum.EDIT: { 
        this.title="Modifier";
        this.data = Question.clone(data);
        this.data.prevOrder = 1
         break; 
      } 
      default: { 
        this.title="Consulter";
        this.data = data;
         break; 
      } 
     }

     this.answersComponent.answers=this.data.answers;
    this.displayModal=true;
  }

  close(){
    this.displayModal=false;
  }
  
  /**
   * 
   * @returns Vrai si un des textarea des réponses n'est pas rempli.
   */
  presenceReponseVide():boolean{
    if(!this.answersComponent || !this.data.type) return false;
    var nbReponsesVides = this.answersComponent.answers.filter(reponse => !reponse.reponse).length;
    return nbReponsesVides > 0;
  }

  /**
   * 
   * @returns Vrai si il y a trop ou pas assez de réponse incorrectes.
   */
  nbReponsesCorrectesIncoherent():boolean{
    if(!this.answersComponent || !this.data.type) return false;
    var nbReponseCorrecte :number = this.answersComponent.answers.filter(reponse => reponse.isCorrect == true).length;
    var invalidCasUnique = this.data.type.toLowerCase() == ResponseType.UNIQUE && nbReponseCorrecte!=1;
    var invalidCasMultiple = this.data.type.toLowerCase()  == ResponseType.MULTIPLE && nbReponseCorrecte<2;
    return invalidCasUnique||invalidCasMultiple
  }

  getIdOrGuid(question:IQuestion):number|string{
    if(question.id) return question.id
    return question.guid;
  }
}
