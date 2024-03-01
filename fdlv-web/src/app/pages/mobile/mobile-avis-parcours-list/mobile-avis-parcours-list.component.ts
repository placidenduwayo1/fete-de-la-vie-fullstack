import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpResponse } from '@angular/common/http';
import { Account } from 'src/app/core/auth/account.model';
import { MobileAvisParcours } from 'src/app/model/mobile-avis-parcours.model';
import { MobileUser } from 'src/app/model/mobile-user.model';
import { MobileUserService } from 'src/app/services/mobile/mobile-users.service';
import { AccountService } from '../../account/account.service';
import { first } from 'rxjs';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { MobileAvisParcoursService } from 'src/app/services/mobile/mobile-avis-parcours.service';

import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { Router } from '@angular/router';
import { CardModule } from 'primeng/card';
import { DialogDeleteComponent } from '../../shared/dialog-delete/dialog-delete.component';
import { ButtonModule } from 'primeng/button';
import { Table, TableModule } from 'primeng/table';
import * as FileSaver from 'file-saver';
import * as xlsx from "xlsx";
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-mobile-avis-parcours-list',
  standalone: true,
  imports: [CommonModule, CardModule, DialogDeleteComponent, ButtonModule, TableModule,FormsModule],
  templateUrl: './mobile-avis-parcours-list.component.html',
  styleUrls: ['./mobile-avis-parcours-list.component.scss']
})
export class MobileAvisParcoursListComponent implements OnInit {
  mobileAvis?: MobileAvisParcours[];
  isLoading = false;
  account!: Account;
  users = new Map<number | undefined, string>();
  excel: { question: number | undefined; reponse: string | undefined; nombre: number | undefined }[] = [];
  storedSearchValue = "";
  @ViewChild('idAP', { static: true }) myTab: Table;
  showConfirmationDialog: boolean = false;
  idToDelete: any;
  constructor(
    protected mobileAvisParcoursService: MobileAvisParcoursService,
    protected mobileUserService: MobileUserService,
    private accountService: AccountService,
    protected alertService: AlertService,
    private router: Router
  ) { }

  loadAll(): void {
    this.isLoading = true;

    this.mobileAvisParcoursService.query().subscribe(
      (res: HttpResponse<MobileAvisParcours[]>) => {
        this.isLoading = false;
        this.mobileAvis = res.body ?? [];
        this.mobileAvis.forEach(avis => {
          if (avis.mobileUserId) {
            this.mobileUserService.find(avis.mobileUserId).subscribe((resUser: HttpResponse<MobileUser>) => {
              if (resUser.body?.pseudo) {
                this.users.set(avis.id, resUser.body.pseudo);
              }
            });
          }
        });
      },
      () => {
        this.isLoading = false;
      }
    );

  }

  getDataToBeExported(){
    this.excel = [];
    this.mobileAvis!.forEach(avis => {
      if (this.excel.find(monAvis => monAvis.question === avis.questionFinParcoursId && monAvis.reponse === avis.avis) === undefined) {
        this.excel.push({ question: avis.questionFinParcoursId, reponse: avis.avis, nombre: 1 });
      } else {
        let indexAvis = this.excel.findIndex(monAvis => monAvis.question === avis.questionFinParcoursId && monAvis.reponse === avis.avis);
        this.excel[indexAvis].nombre = this.excel[indexAvis].nombre! + 1;
      }
    });
  }

  exportExcel() {
    this.getDataToBeExported();
    const worksheet = xlsx.utils.json_to_sheet(this.excel);
    const workbook = { Sheets: { data: worksheet }, SheetNames: ['data'] };
    const excelBuffer: any = xlsx.write(workbook, { bookType: 'xlsx', type: 'array' });
    this.saveAsExcelFile(excelBuffer, 'generated');

  }

  saveAsExcelFile(buffer: any, fileName: string): void {
    let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
    let EXCEL_EXTENSION = '.xlsx';
    const data: Blob = new Blob([buffer], {
      type: EXCEL_TYPE
    });
    FileSaver.saveAs(data, fileName + EXCEL_EXTENSION);
  }

  ngOnInit(): void {
    this.accountService.getAccount().pipe(first()).subscribe({
      next: (response: Account) => this.account = response
    });

    this.loadAll();
    this.storedSearchValue = localStorage.getItem("storedSearchValueAP")? localStorage.getItem("storedSearchValueAP") : "";
    if(this.storedSearchValue){
      this.myTab.filterGlobal(this.storedSearchValue, 'contains')
    }
  }

  onSearchChange(event: any){
    this.storedSearchValue = event.target.value;
    localStorage.setItem("storedSearchValueAP", this.storedSearchValue.toString());
  }

  delete(id: number): void {
    this.mobileAvisParcoursService.delete(id).subscribe({
      next: () => {
        this.alertService.addMessage({ severity: AlertSeverity.SUCCESS, summary: "Avis parcours", detail: "La suppression est effectuée." })
        this.closeConfirmationDialog()
        this.loadAll()
      },
      error: () => this.alertService.addMessage({ severity: AlertSeverity.ERROR, summary: "Avis parcours", detail: "Echec de suppression." })
    });
  }
  openConfirmationDialog(id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }
  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }
  view(parcours: MobileAvisParcours) {
    this.router.navigate(['mobile-avis-parcours-create'], { state: { data: parcours, type: 'edit' } });
  }
  create() {
    this.router.navigateByUrl('mobile-avis-parcours-create');
  }

  first = Number(localStorage.getItem("pageAvis"));
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageAvis", this.first.toString());
}
}

