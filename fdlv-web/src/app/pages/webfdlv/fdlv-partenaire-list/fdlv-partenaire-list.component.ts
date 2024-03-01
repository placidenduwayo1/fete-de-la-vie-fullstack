import { HttpResponse } from '@angular/common/http';
import { Component, LOCALE_ID, OnInit, ViewChild } from '@angular/core';
import { IPartenaire } from '../../../model/partenaire.model';
import { PartenaireService } from '../../../services/webfdlv/partenaire.service';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { Table, TableModule } from 'primeng/table';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ToastModule } from 'primeng/toast';
import { CardModule } from 'primeng/card';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogDeleteComponent } from '../../shared/dialog-delete/dialog-delete.component';
import { Router } from '@angular/router';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { PaginatorModule } from 'primeng/paginator';

@Component({
  selector: 'gmd-fdlv-partenaire-list',
  templateUrl: './fdlv-partenaire-list.component.html',
  styleUrls: ['./fdlv-partenaire-list.component.scss'],
  standalone: true,
  providers: [
    { provide: LOCALE_ID, useValue: 'fr-FR' },
    MessageService,
    ConfirmationService,
  ],
  imports:[
    CommonModule,
    ButtonModule,
    TableModule,
    ToastModule,
    CardModule,
    ConfirmPopupModule,
    DialogDeleteComponent,
    PaginatorModule 
  ]
})
export class FdlvPartenaireListComponent implements OnInit {
  partenaires?: IPartenaire[];
  isLoading = false;
  showConfirmationDialog: boolean = false;
  idToDelete: any;
  storedSearchValue = "";
  @ViewChild('idP', { static: true }) myTab: Table;
  constructor(protected partenaireService: PartenaireService,
    private router: Router,
    private alertService: AlertService) {}

  loadAll(): void {
    this.isLoading = true;
    this.partenaireService.findAll().subscribe({
      next:(res: HttpResponse<IPartenaire[]>) => {
        this.isLoading = false;
        this.partenaires = res.body ?? [];
      },
      error:()=> [],
      complete:() => {
        this.isLoading = false;
      }
     });
  }
  ngOnInit(): void {
    this.loadAll();
    this.storedSearchValue = localStorage.getItem("storedSearchValueP")? localStorage.getItem("storedSearchValueP") : "";
    if(this.storedSearchValue){
      this.myTab.filterGlobal(this.storedSearchValue, 'contains')
    }
  }

  onSearchChange(event: any){
    this.storedSearchValue = event.target.value;
    localStorage.setItem("storedSearchValueP", this.storedSearchValue.toString());
  }
  delete(id: any): void {
   
    this.partenaireService.delete(id).subscribe( {
      next:()=>{
        this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Partenaire", detail:"La suppression est effectuée."})
        this.closeConfirmationDialog()
      this.loadAll()
      },
      error:()=>  this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Partenaire", detail:"Echec de suppression."})

    })
    
  }
  edit(partenaire: any){
    this.router.navigate(['fdlv-partenaire-create'], { state: { data: partenaire.id, type: 'edit' } });
  }
  create(){
    this.router.navigateByUrl('fdlv-partenaire-create');
  }
  openConfirmationDialog(id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }
  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }
  first = Number(localStorage.getItem("page"));
  onPageChange(event: any) {
      this.first = event.first;
      localStorage.setItem("page", this.first.toString());
  }


}
