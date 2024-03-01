import { LOCALE_ID, OnDestroy } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import * as fr from '@angular/common/locales/fr';
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { SelectButtonModule } from 'primeng/selectbutton';
import { InputTextModule } from 'primeng/inputtext';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { ToastModule } from 'primeng/toast';
import { Router } from '@angular/router';
import { Observable, Subject, map, takeUntil, catchError } from 'rxjs';
import { ConfirmationService, MessageService } from 'primeng/api';
import { MessageModule } from 'primeng/message';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ForumService } from 'src/app/services/forum/forum.service';
import { Forum } from 'src/app/model/forum/forum.model';

@Component({
  selector: 'app-fiche-forum',
  standalone: true,
  providers: [
    { provide: LOCALE_ID, useValue: 'fr-FR' },
    MessageService,
    ConfirmationService,
  ],
  templateUrl: './fiche-forum.component.html',
  styleUrls: ['./fiche-forum.component.scss'],
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
export class FicheForumComponent implements OnInit, OnDestroy {
  forums!: Forum[];
  dataForum:Forum={};
  destroy$!: Subject<boolean>;

  constructor (
    private forumService: ForumService,
    private router: Router,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {
    registerLocaleData(fr.default)
  }

  ngOnInit(): void {
    console.log('forum component ngOnInit');
    this.destroy$ = new Subject<boolean>();
    this.forumService.forumsA$ = this.forumService.findAll().pipe(
      takeUntil(this.destroy$),
      map((response) => {
        if (this.forumService.forumSubject.value?.length > 0) {
          this.forumService.forumSubject.next(
            this.forumService.forumSubject.value
          );
        } else {
          this.forumService.forumSubject.next(response);
        }
        return this.forumService.forumSubject.value.sort((forum1, forum2)=>forum1.id-forum2.id).reverse();
      })
    )
    this.forumService.forumsW$ = this.forumService.findAllWithCorrespondantFDLV().pipe(
      takeUntil(this.destroy$),
      map((response) => {
        if (this.forumService.forumSubject.value?.length > 0) {
          this.forumService.forumSubject.next(
            this.forumService.forumSubject.value
          );
        } else {
          this.forumService.forumSubject.next(response);
        }
        return this.forumService.forumSubject.value.sort((forum1, forum2)=>forum1.id-forum2.id).reverse();
      })
    )
  }

  ngOnDestroy(): void {
    this.destroy$.next(true);
  }

  createFicheForum() {
    this.router.navigateByUrl('ficheforum-modif');
  }

  refreshFicheForum(){
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate(['forum/fiche-forum']);
    });
  }
  
  editFicheForum(forum:any){
    this.router.navigate(['ficheforum-modif'], { state: { data: forum , type:'modification'} });
  }

  onConfirmDelete(event: Event, forum:any) {
    console.log('suppression forum!');
    let forumId = forum.id;
    if(forum.isValid === "1"){
      this.messageService.add({
        severity: 'error',
        summary: 'Action non autorisé',
        detail: 'Suppression du forum activé impossible.',
      });
      return;
    }
    this.confirmationService.confirm({
      target: event.target as EventTarget,
      acceptLabel: 'OUI',
      rejectLabel: 'NON',
      message: 'Etes vous sûr(e) de supprimer ce forum?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.forumService.forumsA$ = this.forumService
          .delete(forumId)
          .pipe(
            map(() => {
              const forums = this.forumService.forumSubject.value.filter(
                (forum) => forum.id !== forumId
              );
              this.forumService.forumSubject.next(forums);
              this.messageService.add({
                severity: 'success',
                summary: 'Validé',
                detail: 'Suppression validéee!',
              });
              return this.forumService.forumSubject.value;
            }),
            catchError((err, caught) => {
              console.log(err);
              console.log(caught);
              this.messageService.add({
                severity: 'error',
                summary: 'Erreur service:',
                detail: 'Impossible de supprimer un forum en intervention.',
              });
              return this.forumService.findAll();
            })
          )
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

  get forum$(): Observable<Forum[]> {
    return this.forumService.forumsA$;
  }
  get forumW$(): Observable<Forum[]> {
    return this.forumService.forumsW$;
  }

  first = Number(localStorage.getItem("pageFicheForum"));
  
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageFicheForum", this.first.toString());
  }
}
