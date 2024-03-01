import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';

import { Table, TableModule } from 'primeng/table'
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
import { DialogDeleteComponent } from '../../shared/dialog-delete/dialog-delete.component';
import { QuizzModalComponent } from './quizz-modal/quizz-modal.component';
import { IQuizz, Quizz } from 'src/app/model/quizz.model';
import { HttpErrorResponse } from '@angular/common/http';
import { first } from 'rxjs';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { ModalTypeEnum } from 'src/app/enums/modal-type.enum';
import { QuizzService } from 'src/app/services/quizz.service';
import { AlertService } from 'src/app/shared/alert/alert.service';


@Component({
  selector: 'app-quizz-list',
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
    QuizzModalComponent,
    DialogDeleteComponent],
  templateUrl: './quizz-list.component.html',
  styleUrls: ['./quizz-list.component.scss']
})
export class QuizzListComponent {
  dataList: Quizz[];
  @ViewChild(QuizzModalComponent) modal!: QuizzModalComponent;
  idToDelete: number;
  showConfirmationDialog: boolean;
  storedSearchValue = "";
  @ViewChild('myTabQ', { static: true }) myTab: Table;
  constructor(private quizzService: QuizzService, private alertService: AlertService, private route: Router) { }

  ngOnInit() {
    this.quizzService.findAll().pipe(first()).subscribe(response =>
      this.dataList = response
    );
    this.storedSearchValue = localStorage.getItem("storedSearchValueQuizz")? localStorage.getItem("storedSearchValueQuizz") : "";
    if(this.storedSearchValue){
      this.myTab.filterGlobal(this.storedSearchValue, 'contains')
    }
  }
  onSearchChange(event: any){
    this.storedSearchValue = event.target.value;
    localStorage.setItem("storedSearchValueQuizz", this.storedSearchValue.toString());
  
  }
  /*
  =======================================
  Commun à toutes les listes
  =======================================
  */

  delete(id: any): void {

    this.quizzService.delete(id).pipe(first()).subscribe({
      next: response => {
        console.log(response)
        this.reloadPage()
        this.alertService.addMessage({ severity: AlertSeverity.SUCCESS, summary: 'Suppression', detail: "Le quizz a été supprimé." });
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
        this.reloadPage()
        this.quizzService.findVideoByQuizz(id).subscribe({
          next: (response) => {
            const listVideo = [];
            response.body ? response.body.map(qv => listVideo.push(qv.idVideo )) : [];
            if (listVideo.length !== 0) {
              this.alertService.addMessage({
                severity: AlertSeverity.ERROR, summary: 'Suppression',
                detail: "La suppression du Quizz n'est pas possible. Veuillez supprimer en premier le rattachement des Médias avec les ids suivants : [ " + listVideo +" ]"
              });
            } else {
              this.alertService.addMessage({ severity: AlertSeverity.ERROR, summary: 'Suppression', detail: "Une erreur est survenue ! Le quizz n'a pas pu être supprimé." });
            }
          }
        })

      }
    })

  }

  trackId(index: number, item: IQuizz): number {
    return item.id!;
  }
  openEditForm(id: number) {
    this.modal.open(ModalTypeEnum.EDIT, this.dataList.find((quizz: Quizz) => quizz.id == id));
  }

  openCreateForm() {
    this.modal.open(ModalTypeEnum.CREATE);

  }

  reloadPage() {
    this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.route.navigate(['quizz-list'])
    })
  }

  openConfirmationDialog(id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }
  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }
  first = Number(localStorage.getItem("pageQuizz"));
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageQuizz", this.first.toString());
}
}