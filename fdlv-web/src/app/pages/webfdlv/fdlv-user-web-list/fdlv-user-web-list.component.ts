import { Component, LOCALE_ID, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FdlvUsers } from 'src/app/model/fdlv-user.model';
import { HttpResponse } from '@angular/common/http';
import { FdlvUserService } from 'src/app/services/webfdlv/fdlv-users.service';
import { Account } from 'src/app/core/auth/account.model';
import { DialogModule } from 'primeng/dialog';

import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { CardModule } from 'primeng/card';
import { Table, TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogDeleteComponent } from '../../shared/dialog-delete/dialog-delete.component';
import { AccountService } from '../../account/account.service';
import { first } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-fdlv-user-web-list',
  standalone: true,
  imports: [CommonModule,CardModule, DialogModule, TableModule, ButtonModule, ConfirmPopupModule,
  ToastModule,
  DialogDeleteComponent,FormsModule,],
  providers: [
    { provide: LOCALE_ID, useValue: 'fr-FR' },
    MessageService,
    ConfirmationService,
  ],
  templateUrl: './fdlv-user-web-list.component.html',
  styleUrls: ['./fdlv-user-web-list.component.scss']
})
export class FdlvUserWebListComponent implements OnInit {
  account!: Account;
  fdlvUsers?: FdlvUsers[];
  isLoading = false;
  showConfirmationDialog: boolean = false ;
  idToDelete: any ;
  first = Number(localStorage.getItem("pageUserWeb"));
  storedSearchValue = "";
  @ViewChild('idU', { static: true }) myTab: Table;
  constructor(protected fdlvUserService: FdlvUserService, private router: Router,
    private accountService: AccountService ,private alertService: AlertService) {}

  loadAll(): void {
    this.isLoading = true;

    this.fdlvUserService.findAll().subscribe(
      (res: HttpResponse<FdlvUsers[]>) => {
        this.isLoading = false;
        this.fdlvUsers = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    // Récupération de l'utilisateur afin de savoir qui à creer l'organisateur
    this.accountService.getAccount().pipe(first()).subscribe({
      next: (response: Account) => this.account = response
    });

    this.loadAll();
    this.storedSearchValue = localStorage.getItem("storedSearchValueU")? localStorage.getItem("storedSearchValueU") : "";
    if(this.storedSearchValue){
      this.myTab.filterGlobal(this.storedSearchValue, 'contains')
    }
  }

  onSearchChange(event: any){
    this.storedSearchValue = event.target.value;
    localStorage.setItem("storedSearchValueU", this.storedSearchValue.toString());
  }

  setActive(user: FdlvUsers, isActivated: boolean): void {
    const userActive = {
      id: user.id,
      actif: isActivated,
      modifiePar: this.account.login,
    };
    this.fdlvUserService.updateActive(userActive).subscribe(() => this.loadAll());
  }

  delete(id: number): void {
    this.fdlvUserService.delete(id).subscribe( {
      next:()=>{
        this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Utilisateur", detail:"La suppression est effectuée."})
        this.closeConfirmationDialog()
        this.loadAll()
      },
      error:()=>  this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Utilisateur", detail:"Echec de suppression."})

    })
    
    this.showConfirmationDialog = false;
  }
  create(){
    this.router.navigateByUrl('fdlv-user-create');
  }
  
  edit(fdlvUser: FdlvUsers){
    this.router.navigate(['fdlv-user-create'], { state: { data: fdlvUser.id, type: 'edit' } });

  }
  openConfirmationDialog(id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }
  
  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }

  

  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageUserWeb", this.first.toString());
}
}
