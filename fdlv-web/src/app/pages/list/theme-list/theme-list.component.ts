
import { ToastModule } from 'primeng/toast';

import { TagModule } from 'primeng/tag'
import { PanelModule } from 'primeng/panel'
import { Table, TableModule } from 'primeng/table'
import { FormsModule } from '@angular/forms'
import { InputTextModule } from 'primeng/inputtext'
import { NavbarComponent } from 'src/app/ui/navbar/navbar.component'
import {  Router, RouterOutlet } from '@angular/router'
import { CardModule } from 'primeng/card'
import { ButtonModule } from 'primeng/button'
import { RippleModule } from 'primeng/ripple'
import { DialogModule } from 'primeng/dialog'
import { CheckboxModule } from 'primeng/checkbox'
import { MessageModule } from 'primeng/message'
import { DialogDeleteComponent } from '../../shared/dialog-delete/dialog-delete.component';
import { ThemeModalComponent } from '../theme-list/theme-modal/theme-modal.component';
import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { BehaviorSubject, first, map, take } from 'rxjs';
import { ThemeService } from 'src/app/services/webfdlv/theme.service';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ITheme, Theme } from 'src/app/model/theme.model';


@Component({
  selector: 'app-theme-list',
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
    ThemeModalComponent,
    DialogDeleteComponent],
  templateUrl: './theme-list.component.html',
  styleUrls: ['./theme-list.component.scss']
})
export class ThemeListComponent implements OnInit, OnDestroy {
  dataList: Theme[];
  @ViewChild(ThemeModalComponent) modal!: ThemeModalComponent;
  idToDelete: number;
  showConfirmationDialog: boolean;
  storedSearchValue = "";
  @ViewChild('myTab', { static: true }) myTab: Table;

  constructor(private themeService: ThemeService, private alertService: AlertService, private route: Router){}

  
  ngOnInit(){
    this.storedSearchValue = localStorage.getItem("storedSearchValueTheme")? localStorage.getItem("storedSearchValueTheme") : "";
    if(this.storedSearchValue){
      this.myTab.filterGlobal(this.storedSearchValue, 'contains')
    }
    this.themeService.findAll().pipe(first()).subscribe(response => this.dataList=response.body);
  
  
  }
  ngOnDestroy() { 
   /* debugger
  if(window.history.state.type !== 'edit' && window.history.state.navigationId !== 2){
    // fautil garder le filtre pour la creation aussi ? 
    localStorage.removeItem("pageTheme")
    localStorage.removeItem("storedSearchValue")
  }*/
}
 
  /*
  =======================================
  Commun à toutes les listes
  =======================================
  */

  delete(id: any): void {
  
    this.themeService.find(id).subscribe({
      next: (response) => {
        const listEvent = response.body?.events;
        console.log(listEvent)
        if(listEvent.length !== 0){
          let list: number[] = []
           listEvent.forEach(event => list.push(event.id))
           this.alertService.addMessage({
            severity: AlertSeverity.ERROR, summary: 'Suppression',
            detail: "La suppression du Thème n'est pas possible. Veuillez supprimer en premier le rattachement des Parcours avec les ids suivants : [ " + list +" ]"
          });
         this.closeConfirmationDialog()
        }else{
          this.themeService.delete(id).pipe(first()).subscribe({
            next: response => {
              console.log(response)
              this.reloadPage()
              this.alertService.addMessage({ severity: AlertSeverity.SUCCESS, summary: 'Suppression', detail: "Le thème a été supprimé." });
            },
            error: (error: HttpErrorResponse) => {
              console.log(error)
             this.reloadPage()
              this.alertService.addMessage({ severity: AlertSeverity.ERROR, summary: 'Suppression', detail: "Une erreur est survenue ! Le thème n'a pas pu être supprimé." });
            }
          })
        }
      }
      })


    
  }

  edit(theme: any){
    this.route.navigate(['theme-create'], { state: { data: theme, type: 'edit' } });
  }
  create(){
    this.route.navigateByUrl('theme-create');
  }

  reloadPage() {
    this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.route.navigate(['theme-list'])
    })
  }

  openConfirmationDialog(id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }
  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }

  first = Number(localStorage.getItem("pageTheme"))
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageTheme", this.first.toString());

}

onSearchChange(event: any){
  this.storedSearchValue = event.target.value;
  localStorage.setItem("storedSearchValueTheme", this.storedSearchValue.toString());

}


}
