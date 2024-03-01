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
import localeFr from '@angular/common/locales/fr';
import { StructureService } from 'src/app/services/forum/structure.service';
import { Structure } from 'src/app/model/forum/structure.model';
import { MessagesModule } from 'primeng/messages';

@Component({
  selector: 'app-intervention-structure-create',
  standalone: true,
  templateUrl: './intervention-structure-create.component.html',
  styleUrls: ['./intervention-structure-create.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, CardModule, ToastModule, AccordionModule, MessagesModule, ConfirmDialogModule,
    TagModule, DropdownModule, SelectButtonModule, CalendarModule],
  providers: [MessageService, ConfirmationService, { provide: LOCALE_ID, useValue: 'fr' }],
})
export class InterventionStructureCreateComponent implements OnInit {
  activeIndex: number | undefined = 0;
  interventionStructureForm: FormGroup | undefined;
  inputTextStyle1: string = `text-base text-color surface-overla p-2 border-1 border-solid 
  surface-border border-round appearance-none outline-none focus:border-primary w-full`;
  inputTextStyle2: string = `text-base text-color surface-overla border-1 border-solid 
  surface-border border-round appearance-none outline-none focus:border-primary w-full`;
  ouiNons: any = [{ label: 'Oui', value: '1' }, { label: 'Non', value: '0' }];
  warnColorStyle: string = "color:red; font-weight: bold;";
  containerStyle: string = "border:solid 1px gray; padding:1em";
  forumsArray: Array<Forum> = [];
  asterix: string = '*';
  fiedRequired: string = "champ obligatoire *";
  selectReferenceForum: string;
  selectReferenceStand: string;
  selectCodeBanniere: string;
  conventionSigned: boolean = false;
  conventionNotSigned: boolean = false;
  stands: Array<Stand> = [];
  rolesStructure: Array<RoleStructure> = [];
  bannieres: Array<Banniere> = [];
  nbrCharMin3: number = 10;
  charMin: string = "caratères minimum";

  styleClass1: string = "p-button-success p-button-raised p-button-sm";
  styleClass2: string = `p-button-raised p-button-sm`;
  styleClass3: string = `p-button-warning p-button-raised p-button-sm`;
  structureId: number;
  structure: Structure;

  constructor(private formBuilder: FormBuilder, activatedRoute: ActivatedRoute, private router: Router,
    private forumService: ForumService, private standService: StandService, private banniereService: BanniereService,
    private roleStructureService: RoleStructureService, private interventionStructureService: InterventionStructureService,
    private confirmService: ConfirmationService, private messageService: MessageService, private structureService: StructureService,
    private changeDetectorRef: ChangeDetectorRef) {
    this.structureId = activatedRoute.snapshot.params['structureId'];
    registerLocaleData(localeFr, 'fr');
  }

  ngOnInit(): void {
    this.forumService.findAll().subscribe((data: Array<Forum>) => {
      data.forEach((forum: Forum) => {
        forum.libelle = forum.id + ' '
          + forum.numForum + ' '
          + forum.libelle + ' '
          + forum.lieuForum + ' '
          + forum.adresseForum + ' '
          + forum.codePostal + ' '
          + forum.commune;
        this.forumsArray.push(forum);
      });
    });
    this.standService.getAllStands().subscribe((data: Array<Stand>) => {
      data.forEach((stand: Stand) => {
        stand.libelle = stand.id + ' '
          + stand.forumStandSecteur.id + ' '
          + stand.standPhysique + ' '
          + stand.libelle;

        this.stands.push(stand);
      });
    });
    this.banniereService.getAllBanniere().subscribe((data: Array<Banniere>) => {
      data.forEach((banniere: Banniere) => {
        banniere.libelle = banniere.id + ' '
          + banniere.code + ' '
          + banniere.forumThemeId + ' '
          + banniere.libelle;
        this.bannieres.push(banniere);
      });
    });
    this.roleStructureService.getAllRoleStructures().subscribe((data: Array<RoleStructure>) => {
      data.forEach((roleStructure: RoleStructure) => {
        roleStructure.froRole = roleStructure.froId + ' '
          + roleStructure.froCode + ' '
          + roleStructure.froRole;
        this.rolesStructure.push(roleStructure);
      });
    });
    this.structureService.getStructure(this.structureId)
      .subscribe((data: Structure) => {
        this.structure = data;
        this.interventionStructureForm = this.formBuilder.group({
          fsnId: [{ value: '', disabled: true }],
          forum: ['', Validators.required],
          structure: [{ value: this.structure, disabled: true }],
          roleStructure: ['', Validators.required],
          stand: ['', Validators.required],
          banniere: ['', Validators.required],
          fsnDescription: ['', [Validators.required, Validators.minLength(this.nbrCharMin3)]],
          fsnConventionSigne: ['', Validators.required],
          fsnDateConventionSigne: [null],
          fsnDateRelance01: [null],
          fsnDateRelance02: [null],
          fsnDateRelance03: [null],
          fsnDateRelance04: [null],
          fsnDateRelance05: [null],
          fsnCommentaire: ['', Validators.minLength(this.nbrCharMin3)],
          //sub group for structure
          structureForm: this.formBuilder.group({
            id: [{ value: this.structure.id, disabled: true }],
            code: [{ value: this.structure.code, disabled: true }],
            libelle: [{ value: this.structure.libelle, disabled: true }],
            reference: [{ value: this.structure.reference, disabled: true }]
          })
        });
      });
  }

  onRetour() {
    this.router.navigateByUrl('/structure')
  }
  onForumSelect($event: any) {
    this.selectReferenceForum = $event.reference
  }
  onStandSelect($event: any) {
    this.selectReferenceStand = $event.reference
  }
  onBanniereSelect($event: any) {
    this.selectCodeBanniere = $event.code
  }
  onConventionSigneeCheck($event: any) {
    const dateSignature = this.interventionStructureForm.get('fsnDateConventionSigne').value;
    if ($event.label === "Non") {
      dateSignature.setValidators(null);
      dateSignature.disable();
      this.enableDisableDatesRelance(false);
      this.conventionSigned = true;
    }
    else if ($event.label === "Oui") {
      dateSignature.enable();
      dateSignature.setValidators([Validators.required]);
      this.enableDisableDatesRelance(true);
      this.conventionSigned = true;
    }

    this.changeDetectorRef.detectChanges();
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

  onSave($event: any): any {
    let interventionBean: InterventionStructure = this.interventionStructureForm.value;
    interventionBean.structure = this.structure;
    interventionBean.fsnConventionSigne = this.interventionStructureForm.get('fsnConventionSigne').value.label;
    this.confirmService.confirm({
      target: $event.target as EventTarget,
      acceptLabel: 'Oui',
      rejectLabel: 'Non',
      message: 'Veuillez confirmer la sauvegarder',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.interventionStructureService.createInterventionStructure(interventionBean).subscribe(
          (data: InterventionStructure) => {
            this.messageService.add({
              key: 'save-success',
              severity: 'success',
              detail: 'L\'intervention pour la structure ' + this.structure.id + ' créée avec succès',
              sticky: true
            });
            return data;
          });
      },
      reject: () => {
        this.messageService.add({
          key: 'save-rejected',
          severity: 'error',
          detail: 'Mise en jour annulée!',
        });

        return null;
      }
    });
  }

  onCreer() {
    window.location.reload();
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