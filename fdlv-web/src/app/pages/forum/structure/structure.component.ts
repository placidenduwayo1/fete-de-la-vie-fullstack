import { LOCALE_ID, OnDestroy } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import * as fr from '@angular/common/locales/fr';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StructureService } from 'src/app/services/forum/structure.service';
import { Structure } from 'src/app/model/forum/structure.model';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { SelectButtonModule } from 'primeng/selectbutton';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { Router } from '@angular/router';
import { Observable, Subject, map, of, takeUntil } from 'rxjs';
import { ConfirmationService, MessageService } from 'primeng/api';
import { NiveauResponsabilite } from '../../../model/forum/niveau-responsabilite.model';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TooltipModule } from 'primeng/tooltip';
import { MessagesModule } from 'primeng/messages';
import { NiveauResponsabiliteService } from 'src/app/services/forum/niveau-responsabilite.service';

@Component({
  selector: 'app-structure',
  standalone: true,
  templateUrl: './structure.component.html',
  styleUrls: ['./structure.component.scss'],
  providers: [
    { provide: LOCALE_ID, useValue: 'fr-FR' },
    MessageService,
    ConfirmationService,
  ],
  imports: [
    CommonModule,
    TableModule,
    ButtonModule,
    SelectButtonModule,
    InputTextModule,
    CardModule,
    TagModule,
    ToastModule,
    MessagesModule,
    //new modif debut
    ConfirmDialogModule,
    TooltipModule
    //new modif fin
  ]
})
export class StructureComponent implements OnInit, OnDestroy {
  structure!: Structure[];
  datastructure: Structure;
  destroy$!: Subject<boolean>;


  //new modif debut
  toolTip1: string = "ajouter une intervention";
  toolTip2: string = "modifier la structure";
  toolTip3: string = "supprimer la structure";
  toolTip4: string = "interventions de la structure";
  toolTipPos: string = "top";
  niveauResponsabilite: string;
  structureListNew$: Observable<Structure[]>;
  warnColorStyle: string = "color:red;";
  //new modif dfin

  /*
  1 = Action à diffuser uniquement en interne FDLV 

2 = Action à diffusion restreinte auprès de certaines structures/acteurs de niveau 2 (structure financier du Forum) 

3 = Action à diffusion restreinte auprès de certaines structures/acteurs de niveau 3 (structure intervenant dans l’organisation d’un Forum) 

4 = Action à diffusion restreinte auprès de certaines structures/acteurs de niveaux 2 et 3  

5 = Action à diffusion uniquement aux acteurs intervenant au sein du Forum et aux structures/acteurs de niveau 2 et 3 

9  = Action à diffuser au niveau tout public 
  */

  constructor(
    private structureService: StructureService,
    private router: Router,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private niveauResponsabiliteService: NiveauResponsabiliteService
  ) {
    registerLocaleData(fr.default)
  }

  ngOnInit(): void {
    this.printData1();
  }

  //new modif debut

  private printData1() {
    this.destroy$ = new Subject<boolean>();
    this.structureService.structures$ = this.structureService.getAllStructures().pipe(
      takeUntil(this.destroy$),
      map((structures: Structure[]) => {
        structures.forEach((structure: Structure)=>{
          if(structure.niveauResponsabilite!=null){
            this.niveauResponsabiliteService.getNiveauResponsabiliteByLevel(structure.niveauResponsabilite).subscribe(
              (data: NiveauResponsabilite[])=>{
                let niveauResp: NiveauResponsabilite = data[0];
                structure.niveauResponsabilite = niveauResp
              }
            );
          }
        });
        if (this.structureService.structureSubject.value?.length > 0) {
          this.structureService.structureSubject.next(
            this.structureService.structureSubject.value
          );
        } else {
          this.structureService.structureSubject.next(structures);
        }
        return this.structureService.structureSubject.value.sort((structure1, structure2) => structure1.id - structure2.id).reverse();
      })
    );
  }

  //new modif fin

  ngOnDestroy(): void {
    this.destroy$.next(true);
  }

  createStructure() {
    this.router.navigateByUrl('newstructure');
  }
  
  EditStructure(structure: any) {
    this.router.navigate(['newstructure'], { state: { data: structure, type: 'modification' } });
  }
  onConfirmDelete(event: Event, structureId: number) {
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      acceptLabel: 'Oui',
      rejectLabel: 'Non',
      message: 'Etes vous sûr(e) de supprimer cette structure?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.structureService.structures$ = this.structureService
          .deleteStructure(structureId)
          .pipe(
            map(() => {
              const acteurs = this.structureService.structureSubject.value.filter(
                (structure) => structure.id !== structureId
              );
              this.structureService.structureSubject.next(acteurs);
              this.messageService.add({
                key: 'success-delete',
                severity: 'success',
                detail: 'Suppression validéee!',
              });
              return this.structureService.structureSubject.value;
            })
          );
      },
      reject: () => {
        this.messageService.add({
          key: 'rejected',
          severity: 'error',
          detail: 'Suppression annulée!',
        });
      },
    });
  }

  get structure$(): Observable<Structure[]> {
    return this.structureService.structures$;
  }

  //new modif debut

  onRefresh() {
    this.printData1();
  }
  onAffichierInterventionsStructure() {
    this.router.navigateByUrl('/intervention-structure-print');
  }

  onAddInterventionStructure(structure: Structure) {
    this.router.navigateByUrl("/intervention-structure-create/" + structure.id)
  }

  first: any = Number(localStorage.getItem("pageStructure"));
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageStructure", this.first.toString());
  }

  onPrintInterventionForStructure(structure: Structure) {
    this.router.navigateByUrl("/interventions-structure-print/" + structure.id);
  }
  //new modif fin

}
