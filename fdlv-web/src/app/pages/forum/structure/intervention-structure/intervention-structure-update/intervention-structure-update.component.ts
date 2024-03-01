//new modif debut
import { Component, OnInit, LOCALE_ID, ChangeDetectorRef, AfterViewInit } from '@angular/core';
import { CommonModule, registerLocaleData } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { ToastModule } from 'primeng/toast';
import { AccordionModule } from 'primeng/accordion';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TagModule } from 'primeng/tag';
import { DropdownModule } from 'primeng/dropdown';
import { Forum } from 'src/app/model/forum/forum.model';
import { Stand } from 'src/app/model/forum/stand.model';
import { Banniere } from 'src/app/model/forum/banniere.model';
import { RoleStructure } from 'src/app/model/forum/role-structure.model';
import { SelectButtonModule } from 'primeng/selectbutton';
import { CalendarModule } from 'primeng/calendar';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ActivatedRoute, Router } from '@angular/router';
import { ForumService } from 'src/app/services/forum/forum.service';
import { StandService } from 'src/app/services/forum/stand.service';
import { BanniereService } from 'src/app/services/forum/banniere.service';
import { RoleStructureService } from 'src/app/services/forum/role-structure.service';
import { InterventionStructure } from 'src/app/model/forum/intervention-structure.model';
import { InterventionStructureService } from 'src/app/services/forum/intervention-structure.service';
import { Structure } from 'src/app/model/forum/structure.model';
import localeFr from '@angular/common/locales/fr';
import { MessagesModule } from 'primeng/messages';
import { Observable, map } from 'rxjs';

@Component({
  selector: 'app-intervention-structure-update',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, CardModule, ToastModule, AccordionModule, ConfirmDialogModule,
    TagModule, DropdownModule, SelectButtonModule, CalendarModule, MessagesModule],
  providers: [MessageService, ConfirmationService, { provide: LOCALE_ID, useValue: 'fr' }],
  templateUrl: './intervention-structure-update.component.html',
  styleUrls: ['./intervention-structure-update.component.scss']
})
export class InterventionStructureUpdateComponent implements OnInit, AfterViewInit {

  activeIndex: number | undefined = 0;
  interventionStructureForm: FormGroup | undefined;
  inputTextStyle1: string = `text-base text-color surface-overla p-2 border-1 border-solid 
  surface-border border-round appearance-none outline-none focus:border-primary w-full`;
  inputTextStyle2: string = `text-base text-color surface-overla border-1 border-solid 
  surface-border border-round appearance-none outline-none focus:border-primary w-full`;
  ouiNons: any = [{ label: 'Oui', value: '1' }, { label: 'Non', value: '0' }];
  warnColorStyle: string = "color:red;";
  containerStyle: string = "border:solid 1px gray; padding:1em";
  forums: Forum[];
  forums$:Observable<Forum[]>;
  asterix: string = '*';
  fiedRequired: string = "champ obligatoire *";
  selectReferenceForum: string;
  selectReferenceStand: string;
  selectReferenceBanniere: string;
  conventionSigned: boolean = false;
  conventionNotSigned: boolean = false;

  stands: Stand[];
  rolesStructures: RoleStructure[];
  bannieres: Banniere[];
  nbrCharMin3: number = 10;
  charMin: string = "caratères minimum";

  intinterventionStructureId: number;
  styleClass3: string = `p-button-warning p-button-raised p-button-sm`;

  structure: Structure;
  selectedForum: Forum;
  selectedStand: Stand;

  constructor(private formBuilder: FormBuilder, activatedRoute: ActivatedRoute, private router: Router,
    private forumService: ForumService, private standService: StandService, private banniereService: BanniereService,
    private roleStructureService: RoleStructureService, private interventionStructureService: InterventionStructureService,
    private confirmService: ConfirmationService, private messageService: MessageService, private cd: ChangeDetectorRef) {
    this.intinterventionStructureId = activatedRoute.snapshot.params['fsnId'];
    registerLocaleData(localeFr, 'fr');
  }


  ngOnInit(): void {
    
    this.forumService.findAll().subscribe((data: Array<Forum>) => {
      this.forums = data;

    });
    this.standService.getAllStands().subscribe((data: Array<Stand>) => {
      this.stands = data;
    });
    this.banniereService.getAllBanniere().subscribe((data: Array<Banniere>) => {
      this.bannieres = data;
    });
    this.roleStructureService.getAllRoleStructures().subscribe((data: Array<RoleStructure>) => {
      this.rolesStructures = data;
    });

    this.interventionStructureService.getInterventionStructure(this.intinterventionStructureId).subscribe(
      (data: InterventionStructure) => {
        this.structure = data.structure;
        this.interventionStructureForm = this.formBuilder.group({
          fsnId: [{ value: data.fsnId, disabled: true }],
          forum: [data.forum, Validators.required],
          structure: [{ value: data.structure, disabled: true }],
          roleStructure: [data.roleStructure, Validators.required],
          stand: [data.stand, Validators.required],
          banniere: [data.banniere, Validators.required],
          fsnDescription: [data.fsnDescription, [Validators.required, Validators.minLength(this.nbrCharMin3)]],
          fsnConventionSigne: [data.fsnConventionSigne, Validators.required],
          fsnDateConventionSigne: [data.fsnDateConventionSigne],
          fsnDateRelance01: [data.fsnDateRelance01],
          fsnDateRelance02: [data.fsnDateRelance02],
          fsnDateRelance03: [data.fsnDateRelance03],
          fsnDateRelance04: [data.fsnDateRelance04],
          fsnDateRelance05: [data.fsnDateRelance05],
          fsnCommentaire: [data.fsnCommentaire, Validators.minLength(this.nbrCharMin3)],
          //sub group for structure
          structureForm: this.formBuilder.group({
            id: [{ value: this.structure.id, disabled: true }],
            code: [{ value: this.structure.code, disabled: true }],
            libelle: [{ value: this.structure.libelle, disabled: true }],
            reference: [{ value: this.structure.reference, disabled: true }]
          })
        });
        this.selectedForum = data.forum;
        this.selectedStand = data.stand;
      });
  }

  ngAfterViewInit(): void {
    this.cd.detectChanges();
  }

  isInvalidInterventionStructureForum(): boolean {
    return (
      this.interventionStructureForm.get('forum').invalid ||
      this.interventionStructureForm.get('roleStructure').invalid ||
      this.interventionStructureForm.get('fsnDescription').invalid ||
      this.interventionStructureForm.get('fsnConventionSigne').invalid ||
      this.interventionStructureForm.get('fsnDateConventionSigne').invalid ||
      this.interventionStructureForm.get('fsnCommentaire').invalid
    );
  }


  onRetour() {
    this.router.navigateByUrl('/intervention-structure-print')
  }
  onUpdate($event: Event) {
    let interventionBean: InterventionStructure = new InterventionStructure();

    interventionBean.fsnId = this.interventionStructureForm.get('fsnId').value;
    interventionBean.forum = this.interventionStructureForm.get('forum').value;
    interventionBean.structure = this.structure;
    interventionBean.roleStructure = this.interventionStructureForm.get('roleStructure').value;
    interventionBean.stand = this.interventionStructureForm.get('stand').value;
    interventionBean.banniere = this.interventionStructureForm.get('banniere').value;
    interventionBean.fsnDescription = this.interventionStructureForm.get('fsnDescription').value;
    interventionBean.fsnConventionSigne = this.interventionStructureForm.get('fsnConventionSigne').value.label;
    interventionBean.fsnDateConventionSigne = this.interventionStructureForm.get('fsnDateConventionSigne').value;
    interventionBean.fsnDateRelance01 = this.interventionStructureForm.get('fsnDateRelance01').value;
    interventionBean.fsnDateRelance02 = this.interventionStructureForm.get('fsnDateRelance02').value;
    interventionBean.fsnDateRelance03 = this.interventionStructureForm.get('fsnDateRelance03').value;
    interventionBean.fsnDateRelance04 = this.interventionStructureForm.get('fsnDateRelance04').value;
    interventionBean.fsnDateRelance05 = this.interventionStructureForm.get('fsnDateRelance05').value;

    this.confirmService.confirm({
      target: $event.target as EventTarget,
      acceptLabel: 'Oui',
      rejectLabel: 'Non',
      message: 'Veuillez confirmer la sauvegarder',
      icon: 'pi pi-exclamation-triangle',

      accept: () => {
        this.interventionStructureService.updateInterventionStructure(this.intinterventionStructureId, interventionBean).subscribe(
          (data: InterventionStructure) => {
            this.messageService.add({
              key: 'save-success',
              severity: 'success',
              summary: 'Validé',
              detail: 'Mise en jour bien validée pour ' + data.fsnId,
              sticky: true
            })
          });
      },
      reject: () => {
        this.messageService.add({
          key: 'save-rejected',
          severity: 'error',
          summary: 'Annulé',
          detail: 'Mise en jour annulée!',
        });
      }
    });
  }


  onForumSelect($event: any) {
    this.selectReferenceForum = $event.reference
  }
  onStandSelect($event: any) {
    this.selectReferenceStand = $event.reference
  }
  onBanniereSelect($event: any) {
    this.selectReferenceBanniere = $event.reference
  }

  onConventionSigneeCheck($event: any) {
    const dateSignature = this.interventionStructureForm.get('fsnDateConventionSigne');
    if ($event.label === "Non") {
      dateSignature.setValidators(null);
      dateSignature.disable();
      this.enableDisableDatesRelance(false);
      this.conventionSigned = true;
    }
    if ($event.label === "Oui") {
      dateSignature.enable();
      dateSignature.setValidators([Validators.required]);
      this.enableDisableDatesRelance(true);
      this.conventionSigned = true;
    }
  }

  private enableDisableDatesRelance(state: boolean) {
    const dateRelance01 = this.interventionStructureForm.get('fsnDateRelance01');
    const dateRelance02 = this.interventionStructureForm.get('fsnDateRelance02');
    const dateRelance03 = this.interventionStructureForm.get('fsnDateRelance03');
    const dateRelance04 = this.interventionStructureForm.get('fsnDateRelance04');
    const dateRelance05 = this.interventionStructureForm.get('fsnDateRelance05');
    if (state) {
      dateRelance01.disable();
      dateRelance02.disable();
      dateRelance03.disable();
      dateRelance04.disable();
      dateRelance05.disable();
    }
    else {
      dateRelance01.enable();
      dateRelance02.enable();
      dateRelance03.enable();
      dateRelance04.enable();
      dateRelance05.enable();
    }
  }
  onAnnuler() {
    this.confirmService.confirm({
      acceptLabel: 'Oui',
      rejectLabel: 'Non',
      message: 'Voulez-vous annuller la création de l\'intervention',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        window.location.reload();
        this.messageService.add({
          key: 'save-rejected',
          severity: 'error',
          detail: 'création de l\'intervention annulée!',
        });
      }
    });
  }
}
//new modif fin