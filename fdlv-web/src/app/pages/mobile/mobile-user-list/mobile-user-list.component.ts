import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MobileUser } from 'src/app/model/mobile-user.model';
import { Account } from 'src/app/core/auth/account.model';
import { HttpResponse } from '@angular/common/http';
import { AccountService } from '../../account/account.service';
import { first } from 'rxjs';
import { MobileUserService } from 'src/app/services/mobile/mobile-users.service';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { Router } from '@angular/router';
import { DialogDeleteComponent } from '../../shared/dialog-delete/dialog-delete.component';
import { Table, TableModule } from 'primeng/table';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-mobile-user-list',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule, DialogDeleteComponent, TableModule, FormsModule],
  templateUrl: './mobile-user-list.component.html',
  styleUrls: ['./mobile-user-list.component.scss']
})
export class MobileUserListComponent implements OnInit {
  account!: Account;
  mobileUsers?: MobileUser[];
  isLoading = false;
  showConfirmationDialog: boolean = false;
  idToDelete: any;
  storedSearchValue = "";
  @ViewChild('idUM', { static: true }) myTab: Table;
  constructor(protected mobileUserService: MobileUserService, 
    private accountService: AccountService, private alertService: AlertService, 
    private router: Router) {}

  loadAll(): void {
    this.isLoading = true;

    this.mobileUserService.query().subscribe(
      (res: HttpResponse<MobileUser[]>) => {
        this.isLoading = false;
        this.mobileUsers = res.body ?? [];
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
    this.storedSearchValue = localStorage.getItem("storedSearchValueUM")? localStorage.getItem("storedSearchValueUM") : "";
    if(this.storedSearchValue){
      this.myTab.filterGlobal(this.storedSearchValue, 'contains')
    }
  }

 
  onSearchChange(event: any){
    this.storedSearchValue = event.target.value;
    localStorage.setItem("storedSearchValueUM", this.storedSearchValue.toString());
  
  }
 
  delete(id: number): void {
    this.mobileUserService.delete(id).subscribe( {
      next:()=>{
        this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Utilisateur mobile", detail:"La suppression est effectuée."})
        this.closeConfirmationDialog()
      this.loadAll()
      },
      error:()=>  this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Utilisateur mobile", detail:"Echec de suppression."})
    });
  }
  openConfirmationDialog(id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }
  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }
  edit(user: any){
    this.router.navigate(['mobile-user-create'], { state: { data: user.id, type: 'edit' } });
  }
  create(){
    this.router.navigateByUrl('mobile-user-create');
  }

  first = Number(localStorage.getItem("pageUserMobile"));
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageUserMobile", this.first.toString());
}
}
