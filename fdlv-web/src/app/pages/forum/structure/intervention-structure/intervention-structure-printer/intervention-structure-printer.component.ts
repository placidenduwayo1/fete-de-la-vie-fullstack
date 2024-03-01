//new modif debut
import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InterventionStructure } from 'src/app/model/forum/intervention-structure.model';
import { InterventionStructureService } from 'src/app/services/forum/intervention-structure.service';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TableModule } from 'primeng/table';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { Router } from '@angular/router';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TooltipModule } from 'primeng/tooltip';
import { MessagesModule } from 'primeng/messages';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-intervention-structure-printer',
  standalone: true,
  templateUrl: './intervention-structure-printer.component.html',
  styleUrls: ['./intervention-structure-printer.component.scss'],
  imports: [
    CommonModule,
    ConfirmDialogModule,
    TableModule,
    CardModule,
    ButtonModule,
    ToastModule,
    TooltipModule,
    MessagesModule
  ],
  providers: [MessageService, ConfirmationService]
})
export class InterventionStructurePrinterComponent implements OnInit {
  
  interventionStructureList$ :Observable<Array<InterventionStructure>>;
  inputTextStyle1: string = `text-base text-color surface-overla p-2 border-1 border-solid 
  surface-border border-round appearance-none outline-none focus:border-primary w-full`;
  toolTip1: string = "éditer l\'intervention";
  toolTip2: string = "supprimer l\'intervention";
  toolTipPos: string = "top";

  constructor(
    private interventionStructureService: InterventionStructureService,
    private router: Router, private confirmationService: ConfirmationService,
    private messageService: MessageService) {
  }
  ngOnInit(): void {
    this.printInterventionsStructures();
  }

  private printInterventionsStructures() {
    this.interventionStructureList$ = this.interventionStructureService
    .getAllInterventionStructures();
  }
  
  onRefresh() {
    this.printInterventionsStructures();
  }

  onEditInterventionStructure(interventionStructure: InterventionStructure) {
    this.router.navigateByUrl("/intervention-structure-edit/" + interventionStructure.fsnId);
  }

  onDeleteInterventionStructure(event: Event, fsnId: number) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      acceptLabel: 'Oui',
      rejectLabel: 'Non',
      message: 'Etes vous sûr(e) de supprimer cette intevention?',
      icon: 'pi pi-exclamation-triangle',

      accept: () => {
        this.interventionStructureService.deleteInterventionStructure(fsnId).subscribe(() => {
          this.messageService.add({
            key: 'success-delete',
            severity: 'success',
            detail: 'Suppression validéee!',
            sticky: true
          });
          this.onRefresh();
        })
      },
      reject: () => {
        this.messageService.add({
          key: 'rejected',
          severity: 'error',
          detail: 'Suppression annulée!',
          sticky: true
        });
      }
    })
  }
  onRetour() {
    this.router.navigateByUrl('/structure')
  }

  first: number = Number(localStorage.getItem("pageInterventionStructure"));
  onPageChange($event: any){
    this.first = $event.first;
    localStorage.setItem("pageInterventionStructure", this.first.toString());
  }

}
//new modif fin