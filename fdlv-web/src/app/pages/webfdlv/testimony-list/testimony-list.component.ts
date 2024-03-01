import { Component, OnInit, LOCALE_ID, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { SharedModule } from 'primeng/api';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Table, TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { SelectButtonModule } from 'primeng/selectbutton';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { MessageModule } from 'primeng/message';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogModule } from 'primeng/dialog';
import { ITestimony } from '../../../model/webfdlv/testimony.model';
import { TestimonyService } from '../../../services/webfdlv/testimony.service';
import { DialogDeleteComponent } from '../../shared/dialog-delete/dialog-delete.component';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'fdlv-testimony',
  templateUrl: './testimony-list.component.html',
  styleUrls: ['./testimony-list.component.scss'],
  standalone: true,
  providers: [
    { provide: LOCALE_ID, useValue: 'fr-FR' },
    MessageService,
    ConfirmationService,
  ],
  imports: [SharedModule,
    CommonModule,
    TableModule,
    ButtonModule,
    SelectButtonModule,
    InputTextModule,
    CardModule,
    TagModule,
    ToastModule,
    MessageModule,
    ConfirmPopupModule,
    DialogModule,
    FormsModule,
    //import generique component
    DialogDeleteComponent,

  ],


})
export class TestimoniesComponent implements OnInit {
  testimonies?: ITestimony[];
  isLoading = false;
  showConfirmationDialog: boolean = false
  idToDelete: number = 0
  storedSearchValue = "";
  @ViewChild('idT', { static: true }) myTab: Table;
  constructor(protected testimonyService: TestimonyService,
    private router: Router,
    private alertService: AlertService
  ) { }

  loadAll(): void {
    this.isLoading = true;

    this.testimonyService.query()
      .subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.isLoading = false;
          this.testimonies = res.body ?? [];
        },
        error: () => [],
        complete: () => {
          this.isLoading = false;
        }
      });
  }

  ngOnInit(): void {
    this.loadAll();
    this.storedSearchValue = localStorage.getItem("storedSearchValueT")? localStorage.getItem("storedSearchValueT") : "";
    if(this.storedSearchValue){
      this.myTab.filterGlobal(this.storedSearchValue, 'contains')
    }
  }

  onSearchChange(event: any){
    this.storedSearchValue = event.target.value;
    localStorage.setItem("storedSearchValueT", this.storedSearchValue.toString());
  }
  delete(testimonyId: number): void {
    this.testimonyService.delete(testimonyId).subscribe({
      next:()=>{
        this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Témoignage", detail:"La suppression est effectuée."})
        this.closeConfirmationDialog()
        this.loadAll()
      }, error:()=>  this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Témoignage", detail:"Echec de suppression."})

    })
      
  }

  createTestimony() {
    this.router.navigateByUrl('testimony-create');
  }
  editTestimony(testimony: ITestimony) {
    this.router.navigate(['testimony-create'], { state: { data: testimony.id, type: 'edit' } });
  }

  openConfirmationDialog(id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }

  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }

  first = Number(localStorage.getItem("pageTestimony"))
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageTestimony", this.first.toString());
}
 
}
