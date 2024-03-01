import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core'
import { CommonModule } from '@angular/common'
import { AccordionModule } from 'primeng/accordion'
import { CardModule } from 'primeng/card'
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms'
import { ButtonModule } from 'primeng/button'
import { InputMaskModule } from 'primeng/inputmask'
import { ToastModule } from 'primeng/toast'
import { PasswordModule } from 'primeng/password'
import { Router } from '@angular/router'
import { CalendarModule } from 'primeng/calendar'
import { CheckboxModule } from 'primeng/checkbox'
import { DropdownChangeEvent, DropdownModule } from 'primeng/dropdown'
import { InputTextareaModule } from 'primeng/inputtextarea'
import { SelectButtonModule } from 'primeng/selectbutton'
import { MessageService } from 'primeng/api'
import { DialogModule } from 'primeng/dialog'
import { ModuleApplicatif } from 'src/app/model/forum/module-applicatif.model'
import { PickListModule } from 'primeng/picklist'
import { ModuleApplicatifService } from 'src/app/services/forum/module-applicatif.service'
import { PanelModule } from 'primeng/panel'
import { TimelineModule } from 'primeng/timeline'
import { Acteur } from 'src/app/model/forum/acteur.model'
import { Structure } from 'src/app/model/forum/structure.model'
import { RoleActeur } from 'src/app/model/forum/role-acteur.model'
import { Forum } from 'src/app/model/forum/forum.model'
import { ActeurService } from 'src/app/services/forum/acteur.service'
import { StructureService } from 'src/app/services/forum/structure.service'
import { RoleActeurService } from 'src/app/services/forum/role-acteur.service'
import { ForumService } from 'src/app/services/forum/forum.service'
import { InputTextModule } from 'primeng/inputtext'
import { TagModule } from 'primeng/tag'
import { FileUpload, FileUploadModule } from 'primeng/fileupload'
import { User } from 'src/app/model/user.model'
import { Observable, of, switchMap, tap } from 'rxjs'
import { MessageModule } from 'primeng/message'
import { MediaResourceService } from 'src/app/services/forum/media-resource.service'
import { InterventionActeur } from 'src/app/model/forum/intervention-acteur.model'
import { InterventionActeurService } from 'src/app/services/forum/intervention-acteur.service'

@Component({
  selector: 'app-acteur-form',
  standalone: true,
  imports: [
    CommonModule,
    AccordionModule,
    CardModule,
    FormsModule,
    ButtonModule,
    InputMaskModule,
    ToastModule,
    PasswordModule,
    CalendarModule,
    CheckboxModule,
    DropdownModule,
    InputTextareaModule,
    SelectButtonModule,
    DialogModule,
    PickListModule,
    PanelModule,
    TimelineModule,
    ReactiveFormsModule,
    InputTextModule,
    TagModule,
    FileUploadModule,
    MessageModule,
  ],
  providers: [MessageService],
  templateUrl: './acteur-form.component.html',
  styleUrls: ['./acteur-form.component.scss'],
})
export class ActeurFormComponent implements OnInit {
  @ViewChild('photo') photoActeur!: FileUpload
  modifMode: boolean = false
  acteur: Acteur | undefined = {}
  date!: Date
  activeIndex: number | 0
  uploadedFiles: any[] = []

  modules!: ModuleApplicatif[]
  modulesTarget!: ModuleApplicatif[]

  modulesTimeline!: ModuleApplicatif[]

  visible: boolean = false

  responsables$: Observable<Acteur[]>
  structures$: Observable<Structure[]>
  roleActeurs$: Observable<RoleActeur[]>
  forums$: Observable<Forum[]>
  interventionActeurs$: Observable<InterventionActeur[]>;

  acteursFDLV!: Acteur[]

  structures!: Structure[]

  roleActeurs!: RoleActeur[]
  interventionActeurs!: InterventionActeur[]
  reference:string ='000';
  selectedIntervention: InterventionActeur | undefined;
  passwordHash:string;
  forums!: Forum[]
  actifInactifText: string = 'Inactif'
  acteurForm: FormGroup
  interventionForm: FormGroup
  ouiNons!: any
  actifInactif!: any
  userConnected$: Observable<User>
  asterix
  constructor(
    private route: Router,
    private messageService: MessageService,
    private cdr: ChangeDetectorRef,
    private moduleApplicatifService: ModuleApplicatifService,
    private fb: FormBuilder,
    private acteurService: ActeurService,
    private structureService: StructureService,
    private roleActeurService: RoleActeurService,
    private forumService: ForumService,
    private interventionActeurService: InterventionActeurService,
    private mediaResourceService: MediaResourceService,
  ) {
    this.interventionForm = this.fb.group({
      finId: null,
      acteur: null,
      forum: null,
      roleActeur: null,
      structure: null,
      finDescription: null,
      finDateDebut: new Date(),
      finDateFin: null,
      finCommentaire: null,
      finCharteAssoSigne: null,
      finCharteAssoSigneDate: null,
    })
    this.acteurForm = this.fb.group({
      id: [{ value: null, readOnly: true, disabled: true }],
      login: [null],
      reference: '000',
      name: [null, [Validators.required, Validators.minLength(3)]],
      lastName: [null, [Validators.required, Validators.minLength(3)]],
      email: [
        null,
        [
          Validators.email,
          Validators.required,
          Validators.pattern('^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$'),
        ],
      ],
      passwordHash: [
        null,
        [
          Validators.required,
          Validators.maxLength(100),
          Validators.minLength(8),
        ],
      ],
      numTelPortable: [
        null,
        [
          Validators.maxLength(21),
          Validators.minLength(10),
          Validators.pattern,
        ],
      ],
      numTelBureau: [
        null,
        [
          Validators.maxLength(21),
          Validators.minLength(10),
          Validators.pattern,
        ],
      ],
      actif: '0',
      photoUrl: null,
      acteurFDLV: '0',
      responsable: '0',
      benevole: '1',
      creerUsers: '0',
      correspondant: '0',
      dateDebutValidite: [new Date(), [Validators.required]],
      dateFinValidite: null,
      commentaire: null,
      structure: null,
      structureId: null,
      service: null,
      forumRoleActeur: null,
      forum: null,
      modules: null,
      responsableActor: null,
      createdBy: null,
      interventionActeurs: null
    })
  }

  ngOnInit(): void {
    if (window.history.state.type == 'modifcation') {
      this.modifMode = true
      this.acteur = window.history.state.data
      this.reference=this.acteur.reference
      this.passwordHash=this.acteur.passwordHash
      this.activeIndex = 0
      this.actifInactifText = this.acteur?.actif === '1' ? 'Actif' : 'Inactif'
      this.initialisationFb()
    }
    this.asterix =
      this.acteur?.actif === '1' || this.actifInactifText === '1' ? '*' : ''
    const passwordHashControl = this.acteurForm.get('passwordHash')
    if (passwordHashControl) {
      passwordHashControl[this.modifMode ? 'disable' : 'enable']()
    }
    this.moduleApplicatifService
      .getAllModuleApplicatifs()
      .subscribe((modules) => {
        this.modules = modules
        this.cdr.markForCheck()
      })

    this.modulesTarget = []
    this.modulesTimeline = []

    this.responsables$ = this.acteurService.getResponsables()

    this.structures$ = this.structureService.getAllStructures()
    this.interventionActeurs$ = this.interventionActeurService.query()
    this.interventionActeurs$.subscribe(data => {
      this.interventionActeurs = data;
    });
    this.roleActeurs$ = this.roleActeurService.getAllRoleActeurs()
    this.roleActeurs$.subscribe((roleActeurs) => {
      const roleActeurId9 = roleActeurs.find(
        (roleActeur) => roleActeur.id === 9,
      )
      // Vérifiez si l'élément avec l'ID 9 a été trouvé
      if (roleActeurId9) {
        // Mettez à jour le formulaire avec les valeurs de l'élément trouvé
        this.acteurForm.patchValue({
          forumRoleActeur: roleActeurId9,
        })
      }
    })
    this.forums$ = this.forumService.findAll()
    this.ouiNons = [
      { label: 'OUI', value: '1' },
      { label: 'NON', value: '0' },
    ]
    this.actifInactif = [
      { label: 'Actif', value: '1' },
      { label: 'Inactif', value: '0' },
    ]
    this.acteurForm
      .get('dateDebutValidite')
      .setValidators([Validators.required])
    this.acteurForm.get('id').disable()
    this.userConnected$ = this.acteurService.getUserConnected()
    this.userConnected$.subscribe((user) => {
      if (user) {
        this.acteurForm.patchValue({
          createdBy: user.login,
        })
      }
    })
    this.acteurForm.updateValueAndValidity()
    this.cdr.detectChanges()
  }
  initialisationFb() {
    this.acteurForm = this.fb.group({
      id: this.acteur?.id,
      login: this.acteur?.login,
      reference: this.acteur?.reference,
      name: this.acteur?.name,
      lastName: this.acteur?.lastName,
      email: this.acteur?.email,
      passwordHash: this.acteur?.passwordHash,
      numTelPortable: this.acteur?.numTelPortable,
      numTelBureau: this.acteur?.numTelBureau,
      photoUrl: this.acteur?.photoUrl,
      acteurFDLV: this.acteur?.acteurFDLV,
      responsable: this.acteur?.responsable,
      benevole: this.acteur?.benevole,
      creerUsers: this.acteur?.creerUsers,
      correspondant: this.acteur?.correspondant,
      dateDebutValidite: this.acteur?.dateDebutValidite
        ? new Date(this.acteur?.dateDebutValidite)
        : null,
      dateFinValidite: this.acteur?.dateFinValidite
        ? new Date(this.acteur?.dateFinValidite)
        : null,
      commentaire: this.acteur?.commentaire,
      createdByActor: this.acteur?.createdByActor,
      structure: this.acteur?.structureId?.id,
      structureId: this.acteur?.structureId,
      service: this.acteur?.service,
      forumRoleActeur: this.acteur?.forumRoleActeur,
      forum: this.acteur?.forum ?? { id: 0 },
      actif: this.acteur?.actif ?? '0',
      createdBy: this.acteur?.createdBy,
      responsableActor: this.acteur?.responsableActor,
      interventionActeurs: this.acteur?.interventionActeurs
    })
    this.interventionForm = this.fb.group({
      finId: this.acteur?.interventionActeurs[0]?.finId,
      acteur: this.acteur?.interventionActeurs[0]?.acteur,
      forum: this.acteur?.interventionActeurs[0]?.forum,
      roleActeur: this.acteur?.interventionActeurs[0]?.roleActeur,
      structure: this.acteur?.interventionActeurs[0]?.structure,
      finDescription: this.acteur?.interventionActeurs[0]?.finDescription,
      finDateDebut: this.acteur?.interventionActeurs[0]?.finDateDebut,
      finDateFin: this.acteur?.interventionActeurs[0]?.finDateFin,
      finCommentaire: this.acteur?.interventionActeurs[0]?.finCommentaire,
      finCharteAssoSigne: this.acteur?.interventionActeurs[0]?.finCharteAssoSigne,
      finCharteAssoSigneDate: this.acteur?.interventionActeurs[0]?.finCharteAssoSigneDate,
    })
  }
  toggleAccordion(index: number) {
    if (this.activeIndex === index) {
      this.activeIndex = -1;
    } else {
      if (index === 2 && this.isInvalidIdentiteActeur()) {
        return;
      }
      this.activeIndex = index;
    }
    this.updateValidation();
    this.cdr.detectChanges();
  }
  toggleActifInactif() {
    // Basculer entre "Actif" et "Inactif"
    this.actifInactifText =
      this.actifInactifText === 'Inactif' ? 'Actif' : 'Inactif'
    this.acteurForm.patchValue({
      actif: this.actifInactifText === 'Actif' ? 1 : 0,
    })
    this.updateValidation()
    this.isInvalidRoleActeur()
    this.isInvalidInterventionActeur()
    this.asterix = this.actifInactifText === 'Actif' ? '*' : ''
    this.cdr.detectChanges()
  }
  showDialog() {
    this.visible = true
  }
  onAnnulerChoixModule() {
    this.visible = false
  }
  onRetour() {
    this.route.navigateByUrl('forum/acteurs')
  }
  onValiderChoixModule() {
    // to traitement valider modules choisis
    this.modulesTimeline = []
    this.modulesTarget.forEach((module) => console.log(module.id))
    this.modulesTimeline = this.modulesTarget
    this.visible = false
  }
  onDateDebutChange() {
    const endDate = new Date(this.acteurForm.get('dateDebutValidite').value)
    endDate.setDate(
      this.acteurForm.get('dateDebutValidite').value.getDate() + 7,
    )
    this.acteurForm.patchValue({
      dateFinValidite: endDate,
    })
  }
  onDateFinChange() {
    const dateDebutValidite = this.acteurForm.get('dateDebutValidite').value
    const dateFinValidite = this.acteurForm.get('dateFinValidite').value
    if (dateFinValidite < dateDebutValidite) {
      this.acteurForm.get('dateFinValidite').setErrors({
        invalidDateRange:
          'La date de fin doit être postérieure à la date de début',
      })
    } else {
      this.acteurForm.get('dateFinValidite').setErrors(null)
    }
  }
  onForumInterventionChange(event: DropdownChangeEvent): void {
    const forumId = event.value?.id;
    if (forumId) {
      this.forumService.findOne(forumId).subscribe(
        (forum) => {  // Fonction de rappel pour le cas de succès
          this.acteurForm.patchValue({
            reference: forum.body?.reference,
            forum: forum.body,
          });
          this.reference=forum.body?.reference
          this.interventionForm.reset();
          const forumIdToFilter = forum.body.id;
          const interventionsForForum = this.interventionActeurs.filter(intervention => intervention.forum.id === forumIdToFilter);
          this.interventionActeurs$ = of(interventionsForForum);
          this.acteurForm.updateValueAndValidity()
          this.interventionForm.updateValueAndValidity()
          this.cdr.detectChanges();
        },
        (error) => {  // Fonction de rappel pour le cas d'erreur
          console.error('Une erreur s\'est produite lors de la récupération du forum :', error);
          // Gérez l'erreur selon vos besoins
        }
      );
    } else {
      this.acteurForm.patchValue({
        reference: '000',
        forum: null,
      });
      this.reference='000'
      this.acteurForm.updateValueAndValidity()
      this.interventionForm.updateValueAndValidity()
      this.cdr.detectChanges();
    }
  }
  onInterventionChange(event: DropdownChangeEvent) {
    this.interventionForm.reset();
    this.selectedIntervention = event.value;
    this.interventionForm.setValue({
      finId: event.value.finId,
      acteur: this.acteurForm.value,
      forum: event.value.forum,
      roleActeur: event.value.roleActeur,
      structure: event.value.structure,
      finDescription: event.value.finDescription,
      finDateDebut: event.value.finDateDebut,
      finDateFin: event.value.finDateFin,
      finCommentaire: event.value.finCommentaire,
      finCharteAssoSigne: event.value.finCharteAssoSigne,
      finCharteAssoSigneDate: event.value.finCharteAssoSigneDate,
    });
    this.interventionForm.updateValueAndValidity()
  }
  // Gestion de l'ajout photo
  UploadPhoto() {
    if (this.photoActeur && this.photoActeur.files.length > 0) {
      const photo = this.photoActeur.files[0]
      const formData = new FormData()
      formData.append('media', photo)
      formData.append('path', 'fdlv/FORUM/trombinoscope/')
      formData.append(
        'name',
        this.acteurForm.value.id + '' + this.acteurForm.value.login,
      )
      return this.mediaResourceService.addPhoto(formData).pipe(
        tap((response) => {
          const parsedResponse = JSON.parse(response)
          this.acteurForm.patchValue({
            photoUrl: parsedResponse.mediaUrl,
          })
        }),
      )
    }
    return of(null)
  }
  onUpdateActeur(acteur: any) {
    const passwordHashControl = this.acteurForm.get('passwordHash')
    passwordHashControl['enable']()
    if (this.photoActeur && this.photoActeur.files.length > 0) {
      if (acteur.photoUrl) {
        const proceed = window.confirm('Une image enregistrée sera écrasée. Voulez-vous continuer?');
        if (!proceed) {
          return;
        }
      }
      // Télécharger la nouvelle photo
      this.UploadPhoto().subscribe({
        next: () => {
          // Une fois la photo téléchargée, mettre à jour l'acteur
          this.updateActeur(acteur);
        },
        error: (uploadError) => {
          console.error('Error during photo upload:', uploadError);
        },
      });
    } else {
      // Si aucune nouvelle photo n'est téléchargée, mettre à jour directement l'acteur
      this.updateActeur(acteur);
    }
  }
  updateActeur(acteur: any) {
    acteur.passwordHash=this.passwordHash
    this.acteurService.partialUpdateActeur(acteur.id, this.acteurForm.value).subscribe({
      next: (updatedActeur) => {
        // Mettre à jour la liste des acteurs dans le service
        this.acteurService.acteurSubject.value.unshift(updatedActeur);
        this.acteurService.acteurSubject.next(this.acteurService.acteurSubject.value);

        // Afficher un message de succès
        this.messageService.add({
          severity: 'success',
          summary: 'Acteur',
          detail: 'Acteur bien modifié!',
        });
      },
      error: (updateError) => {
        console.error('Error updating acteur:', updateError.error?.title);
      },
    });
  }
  onSaveActeur() {
    if (!this.acteurForm.invalid) {
      this.UploadPhoto()
        .pipe(
          switchMap(() =>
            this.acteurService.createActeur(this.acteurForm.value),
          ),
        )
        .subscribe({
          next: (acteurCreated) => {
            this.acteurService.acteurSubject.value.unshift(acteurCreated)
            this.acteurService.acteurSubject.next(
              this.acteurService.acteurSubject.value,
            )
            this.messageService.add({
              severity: 'success',
              summary: 'Acteur',
              detail: 'Acteur bien ajouté!',
            })
          },
          error: (error) => {
            console.error("Erreur lors de l'appel de l'un des services", error)
          },
        })
    }
  }
  onUpdateIntervention(interventionForm, acteurForm) {
    acteurForm.interventionActeurs = null
    acteurForm.reference=this.reference
    acteurForm.passwordHash=this.passwordHash
    interventionForm.acteur = acteurForm
    interventionForm.acteur.id=this.acteur.id
    this.interventionActeurService.partialUpdate(interventionForm).subscribe({
      next: (updatedIntervention) => {
        this.interventionActeurService.interventionActeurSubject.value.unshift(updatedIntervention)
        this.interventionActeurService.interventionActeurSubject.next(
          this.interventionActeurService.interventionActeurSubject.value,
        )
        this.messageService.add({
          severity: 'success',
          summary: 'Intervention Acteur',
          detail: 'Intervention acteur bien modifié!',
        })
      },
      error: (error) => {
        console.error('Error title:', error.error?.title)
      },
    })
  }
  onSaveIntervention(intervention) {
    this.acteurService.createActeur(this.acteurForm.value)
      .pipe(
        switchMap((acteurId) => {
          intervention.acteur = acteurId;
          return this.interventionActeurService.create(intervention);
        }),
      )
      .subscribe({
        next: (intervention) => {
          this.interventionActeurService.interventionActeurSubject.value.unshift(intervention);
          this.interventionActeurService.interventionActeurSubject.next(this.interventionActeurService.interventionActeurSubject.value);
          this.messageService.add({
            severity: 'success',
            summary: 'intervention',
            detail: 'intervention bien ajouté!',
          });
        },
        error: (error) => {
          console.error("Erreur lors de l'appel de l'un des services", error);
        },
      });
  }

  updateValidation() {
    const requiredValidator =
      this.acteurForm.value.actif === 1 ? Validators.required : null
    // Mettre à jour la validation des champs spécifiques

    if (this.actifInactifText === 'Inactif') {
      this.acteurForm.get('structure').setErrors(null)
      this.acteurForm.get('forumRoleActeur').setErrors(null)
      this.acteurForm.get('responsableActor').setErrors(null)
      this.acteurForm.clearValidators()
    }
    this.acteurForm.get('numTelPortable').setValidators(requiredValidator)
    this.acteurForm.get('acteurFDLV').setValidators(requiredValidator)
    this.acteurForm.get('creerUsers').setValidators(requiredValidator)
    this.acteurForm.get('correspondant').setValidators(requiredValidator)
    this.acteurForm.get('structure').setValidators(requiredValidator)
    this.acteurForm.get('structureId').setValidators(requiredValidator)
    this.acteurForm.get('forumRoleActeur').setValidators(requiredValidator)
    this.acteurForm.get('forum').setValidators(requiredValidator)
    this.acteurForm.get('responsableActor').setValidators(requiredValidator)
    // Mettre à jour la validité du formulaire
    this.acteurForm.updateValueAndValidity()
    this.cdr.detectChanges()
  }
  isInvalidIdentiteActeur(): boolean {
    return (
      this.acteurForm.get('lastName')?.invalid ||
      this.acteurForm.get('name')?.invalid ||
      this.acteurForm.get('email')?.invalid ||
      this.acteurForm.get('passWord')?.invalid ||
      this.acteurForm.get('numTelPortable')?.invalid
    )
  }
  isInvalidRoleActeur(): boolean {
    return (
      this.acteurForm.get('forumRoleActeur')?.invalid ||
      this.acteurForm.get('responsableActor')?.invalid ||
      this.acteurForm.get('structure')?.invalid
    )
  }
  isInvalidInterventionActeur(): boolean {
    return this.acteurForm.get('forum')?.invalid
  }
}
