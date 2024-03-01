import { HttpResponse } from '@angular/common/http';
import { Component, LOCALE_ID, OnInit, ViewChild } from '@angular/core';
import { InfosPageWeb } from '../../../model/webfdlv/fdlv-infos-page-web.model';
import { InfosPageWebService } from '../../../services/webfdlv/fdlv-infos-page-web.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { CommonModule, registerLocaleData } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { Table, TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { CardModule } from 'primeng/card';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import localeFr from '@angular/common/locales/fr';
import { DialogDeleteComponent } from '../../shared/dialog-delete/dialog-delete.component';
import { Router } from '@angular/router';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { FormsModule } from '@angular/forms';
registerLocaleData(localeFr);

@Component({
  selector: 'fdlv-info-page-web',
  templateUrl: './fdlv-info-page-web-list.component.html',
  styleUrls: ['./fdlv-info-page-web-list.component.scss'],
  standalone: true,
  providers: [
    { provide: LOCALE_ID, useValue: 'fr-FR' },
    MessageService,
    ConfirmationService,
  ],
  imports: [
    CommonModule,
    ButtonModule,
    TableModule,
    ToastModule,
    CardModule,
    ConfirmPopupModule,
    DialogDeleteComponent,
    FormsModule,

  ]
})
export class FdlvInfoPageWebComponent implements OnInit {
  infosPageWeb!: InfosPageWeb[];
  isLoading = false;
  showConfirmationDialog: boolean = false;
  idToDelete: any;
  storedSearchValue = "";
  @ViewChild('idI', { static: true }) myTab: Table;
  constructor(private infosPageWebService: InfosPageWebService, private router: Router, private alertService: AlertService) { }
  ngOnInit(): void {
    this.loadAll();
    this.storedSearchValue = localStorage.getItem("storedSearchValueI")? localStorage.getItem("storedSearchValueI") : "";
    if(this.storedSearchValue){
      this.myTab.filterGlobal(this.storedSearchValue, 'contains')
    }
  }

  onSearchChange(event: any){
    this.storedSearchValue = event.target.value;
    localStorage.setItem("storedSearchValueI", this.storedSearchValue.toString());
  }
  loadAll(): void {
    this.isLoading = true;
    this.infosPageWebService.getAllInfos().subscribe({
      next: (infos: HttpResponse<InfosPageWeb[]>) => {
        this.isLoading = false;
        this.infosPageWeb = infos.body ?? [];
      }, error: () => [],
      complete: () => this.isLoading = false
    }

    );

  }

  trackId(index: number, item: InfosPageWeb): number {
    return item.id;
  }

  delete(infoId: number): void {
    this.infosPageWebService.delete(infoId).subscribe({
      next: () => {
        this.alertService.addMessage({ severity: AlertSeverity.SUCCESS, summary: "Information", detail: "La suppression est effectuée." })
        this.closeConfirmationDialog()
        this.loadAll()
      },
      error: () => this.alertService.addMessage({ severity: AlertSeverity.ERROR, summary: "Information", detail: "Echec de suppression." })

    })
  }
  edit(info: any) {
    this.router.navigate(['info-page-web-create'], { state: { data: info.id, type: 'edit' } });

  }
  create() {
    this.router.navigateByUrl('info-page-web-create');
  }
  openConfirmationDialog(id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }
  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }
  first = Number(localStorage.getItem("pageInfo"))
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageInfo", this.first.toString());
}
}
