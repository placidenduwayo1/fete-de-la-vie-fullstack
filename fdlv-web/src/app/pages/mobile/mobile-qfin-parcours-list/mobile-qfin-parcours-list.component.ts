import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MobileQuestionFinParcours } from 'src/app/model/mobile-qfin-parcours.model';
import { HttpResponse } from '@angular/common/http';
import { Account } from 'src/app/core/auth/account.model';
import { AccountService } from '../../account/account.service';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { first } from 'rxjs';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { Router } from '@angular/router';
import { MobileQfinParcoursService } from 'src/app/services/mobile/mobile-qfin-parcours.service';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { DialogDeleteComponent } from '../../shared/dialog-delete/dialog-delete.component';
import { Table, TableModule } from 'primeng/table';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-mobile-qfin-parcours-list',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule, DialogDeleteComponent ,TableModule, FormsModule],
  templateUrl: './mobile-qfin-parcours-list.component.html',
  styleUrls: ['./mobile-qfin-parcours-list.component.scss']
})
export class MobileQfinParcoursListComponent implements OnInit {
  mobileQuestions?: MobileQuestionFinParcours[];
  isLoading = false;
  account!: Account;
  showConfirmationDialog: boolean = false;
  idToDelete: any;
  storedSearchValue = "";
  @ViewChild('idPA', { static: true }) myTab: Table;

  constructor(
    protected mobileQfinParcoursService: MobileQfinParcoursService,
    private accountService: AccountService,
    protected alertService: AlertService,
    private router: Router
  ) {}

  loadAll(): void {
    this.isLoading = true;

    this.mobileQfinParcoursService.query().subscribe(
      (res: HttpResponse<MobileQuestionFinParcours[]>) => {
        this.isLoading = false;
        this.mobileQuestions = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.accountService.getAccount().pipe(first()).subscribe({
      next:(response:Account) => 
      this.account = response 
    });
    this.loadAll();
    this.storedSearchValue = localStorage.getItem("storedSearchValuePA")? localStorage.getItem("storedSearchValuePA") : "";
    if(this.storedSearchValue){
      this.myTab.filterGlobal(this.storedSearchValue, 'contains')
    }
  }

  onSearchChange(event: any){
    this.storedSearchValue = event.target.value;
    localStorage.setItem("storedSearchValuePA", this.storedSearchValue.toString());
  
  }

  delete(id: number): void {
    this.mobileQfinParcoursService.delete(id).subscribe( {
      next:()=>{
        this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Parcours", detail:"La suppression est effectuée."})
        this.closeConfirmationDialog()
      this.loadAll()
      },
      error:()=>  this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Parcours", detail:"Echec de suppression."})
    });
  }
  openConfirmationDialog(id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }
  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }
  edit(parcours: MobileQuestionFinParcours){
    this.router.navigate(['mobile-qfin-parcours-create'], { state: { data: parcours, type: 'edit' } });
  }
  create(){
    this.router.navigateByUrl('mobile-qfin-parcours-create');
  }

  first = Number(localStorage.getItem("pageQFinParcours"));
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageQFinParcours", this.first.toString());
}
}
