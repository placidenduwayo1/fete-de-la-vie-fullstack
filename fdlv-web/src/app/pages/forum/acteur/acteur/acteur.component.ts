import { LOCALE_ID, OnDestroy } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import * as fr from '@angular/common/locales/fr';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActeurService } from 'src/app/services/forum/acteur.service';
import { Acteur } from 'src/app/model/forum/acteur.model';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { SelectButtonModule } from 'primeng/selectbutton';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { Router } from '@angular/router';
import { Observable, Subject, map, take, takeUntil } from 'rxjs';
import { ConfirmationService, MessageService } from 'primeng/api';
import { MessageModule } from 'primeng/message';
import { ConfirmPopupModule } from 'primeng/confirmpopup';

@Component({
  selector: 'app-acteur',
  standalone: true,
  providers: [
    { provide: LOCALE_ID, useValue: 'fr-FR' },
    MessageService,
    ConfirmationService,
  ],
  templateUrl: './acteur.component.html',
  styleUrls: ['./acteur.component.scss'],
  imports: [
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
  ],
})
export class ActeurComponent implements OnInit, OnDestroy {
  acteurs!: Acteur[];
  dataacteur:Acteur={};
  destroy$!: Subject<boolean>;

  constructor (
    private acteurService: ActeurService,
    private router: Router,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private route: Router
  ) {
    registerLocaleData(fr.default)
  }

  ngOnInit(): void {
    console.log('Acteur component ngOnInit');
    this.destroy$ = new Subject<boolean>();
    this.acteurService.acteurs$ = this.acteurService.getAllActeurs().pipe(
      takeUntil(this.destroy$),
      map((response) => {
        if (this.acteurService.acteurSubject.value?.length > 0) {
          this.acteurService.acteurSubject.next(
            this.acteurService.acteurSubject.value
          );
        } else {
          this.acteurService.acteurSubject.next(response);
        }
        return this.acteurService.acteurSubject.value.sort((acteur1, acteur2)=>acteur1.id-acteur2.id).reverse();
      })
    )
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
  }

  createActeur() {
    this.router.navigateByUrl('forum/newacteur');
  }
  EditActeur(acteur:any){
    this.router.navigate(['forum/newacteur'], { state: { data: acteur , type:'modifcation'} });
  }
  onConfirmDelete(event: Event, acteurId: number) {
    console.log('suppression acteur!');
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      acceptLabel: 'OUI',
      rejectLabel: 'NON',
      message: 'Etes vous sûr(e) de supprimer ce utilisateur?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.acteurService.acteurs$ = this.acteurService
          .deleteActeur(acteurId)
          .pipe(
            map(() => {
              const acteurs = this.acteurService.acteurSubject.value.filter(
                (acteur) => acteur.id !== acteurId
              );
              this.acteurService.acteurSubject.next(acteurs);
              this.messageService.add({
                severity: 'success',
                summary: 'Validé',
                detail: 'Suppression validéee!',
              });
              return this.acteurService.acteurSubject.value;
            })
          );
      },
      reject: () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Annulé',
          detail: 'Suppression annulée!',
        });
      },
    });
  }

  onActiveDesactiverActeur(acteurId: number) {
    this.acteurService.acteurs$ = this.acteurService
      .activerDesactiverActeur(acteurId)
      .pipe(
        take(1),
        map((Response) => {
          const index = this.acteurService.acteurSubject.value.findIndex(
            (a) => a.id === acteurId
          );
          this.acteurService.acteurSubject.value[index] = Response;
          if (Response.actif === '0') {
            this.messageService.add({
              severity: 'success',
              summary: 'Acteur',
              detail: 'Acteur désactivé!',
            });
          } else {
            this.messageService.add({
              severity: 'success',
              summary: 'Acteur',
              detail: 'Acteur activé!',
            });
          }
          this.acteurService.acteurSubject.next(
            this.acteurService.acteurSubject.value
          );
          return this.acteurService.acteurSubject.value;
        })
      );
  }

  get acteur$(): Observable<Acteur[]> {
    return this.acteurService.acteurs$;
  }

  first = Number(localStorage.getItem("pageActeur"));
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageActeur", this.first.toString());
}
reloadPage () {
  this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
    this.route.navigate(['forum/acteurs'])
  })
}

}
