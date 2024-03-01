//new modif debut
import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InterventionStructure } from 'src/app/model/forum/intervention-structure.model';
import { InterventionStructureService } from 'src/app/services/forum/intervention-structure.service';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TableModule } from 'primeng/table';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TooltipModule } from 'primeng/tooltip';
import { MessagesModule } from 'primeng/messages';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-interventions-for-structure',
  standalone: true,
  templateUrl: './interventions-for-structure.component.html',
  styleUrls: ['./interventions-for-structure.component.scss'],
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
export class InterventionsForStructureComponent implements OnInit{

  structureId: number;
  interventionsStructures$: Observable<Array<InterventionStructure>>;
  inputTextStyle1: string = `text-base text-color surface-overla p-2 border-1 border-solid 
  surface-border border-round appearance-none outline-none focus:border-primary w-full`;
  nbOfInterventions: number;
  toolTip1: string = "éditer l\'intervention";
  toolTip2: string = "supprimer l\'intervention";
  toolTipPos: string = "top";
  warnColorStyle: string = "color:red;font-weight: bold;";

  constructor(activatedRoute: ActivatedRoute, private router:Router, private interventionStructureService: InterventionStructureService,
    private confirmationService: ConfirmationService, private messageService: MessageService){
    this.structureId = activatedRoute.snapshot.params['structureId'];
  }

  ngOnInit(): void {
   this.printInterventionsForStrcuture();
  }

  private printInterventionsForStrcuture(){
    this.interventionsStructures$ = this.interventionStructureService.getInterventionForStructure(this.structureId);
    let interventionsStructureTab: Array<InterventionStructure> = [];
    this.interventionsStructures$.subscribe((data: Array<InterventionStructure>)=>{
      interventionsStructureTab = data;
      if(interventionsStructureTab.length>0){
        this.nbOfInterventions = interventionsStructureTab.length;
      }
    });
  }

  first: number = Number(localStorage.getItem("pageInterventionsForStructure"));
  onPageChange($event: any){
    this.first = $event.first;
    localStorage.setItem("pageInterventionsForStructure", this.first.toString());
  }

  onRetour() {
    this.router.navigateByUrl('/structure')
  }
  onRefresh(){
    this.interventionsStructures$ = this.interventionStructureService.getInterventionForStructure(this.structureId);
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
}
//new modif debut