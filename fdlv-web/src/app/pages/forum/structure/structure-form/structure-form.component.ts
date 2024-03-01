import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccordionModule } from 'primeng/accordion';
import { CardModule } from 'primeng/card';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputMaskModule } from 'primeng/inputmask';
import { ToastModule } from 'primeng/toast';
import { PasswordModule } from 'primeng/password';
import { Router } from '@angular/router';
import { CalendarModule } from 'primeng/calendar';
import { CheckboxModule } from 'primeng/checkbox';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { SelectButtonModule } from 'primeng/selectbutton';
import { ConfirmationService, MessageService } from 'primeng/api';
import { DialogModule } from 'primeng/dialog';
import { PickListModule } from 'primeng/picklist';
import { PanelModule } from 'primeng/panel';
import { TimelineModule } from 'primeng/timeline';
import { Acteur } from 'src/app/model/forum/acteur.model';
import { Structure } from 'src/app/model/forum/structure.model';
import { ActeurService } from 'src/app/services/forum/acteur.service';
import { StructureService } from 'src/app/services/forum/structure.service';
import { InputTextModule } from 'primeng/inputtext';
import { TagModule } from 'primeng/tag';
import { FileUpload, FileUploadModule } from 'primeng/fileupload';
import { Observable, map } from 'rxjs';
import { MessageModule } from 'primeng/message';
import { MediaResourceService } from 'src/app/services/forum/media-resource.service';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { InterventionStructure } from 'src/app/model/forum/intervention-structure.model';
import { Stand } from 'src/app/model/forum/stand.model';
import { Banniere } from 'src/app/model/forum/banniere.model';
import { RoleStructure } from 'src/app/model/forum/role-structure.model';
import { NiveauResponsabiliteService } from 'src/app/services/forum/niveau-responsabilite.service';
import { NiveauResponsabilite } from 'src/app/model/forum/niveau-responsabilite.model';



@Component({
  selector: 'app-structure-form',
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
    ConfirmDialogModule
  ],
  providers: [MessageService,/* new modif debut*/ConfirmationService/* new modif fin*/],
  templateUrl: './structure-form.component.html',
  styleUrls: ['./structure-form.component.scss'],

})
export class StructureFormComponent implements OnInit {
  @ViewChild('logoUrl') logoUrl!: FileUpload;
  @ViewChild('charteUrl') charteUrl!: FileUpload;
  modifMode: boolean = false;
  structure: Structure | undefined;
  date!: Date;
  activeIndex: number | undefined = 0;
  structures!: Structure[];

  //new modif debut
  structureForm: FormGroup;
  interventionStructure: InterventionStructure | undefined;
  interventionStrutcureServiceMode: Observable<InterventionStructure>;
  stands: Array<Stand> = [];
  bannieres: Array<Banniere> = [];
  rolesStructure: Array<RoleStructure> = [];
  //new modif fin
  contacts$: Observable<Acteur[]>;
  //new modif debut
  fiedRequired: string = "champ obligatoire *";
  telPattern: string = "^[+]|^[00]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$";
  emailPattern: RegExp = new RegExp(/^[\w\.-]+@[a-zA-Z\d\.-]+\.[a-zA-Z]{2,}$/);
  zipCodePattern: RegExp = new RegExp(/^[a-zA-Z0-9\s]{0,10}$/);
  charMin: string = "caratères minimum";
  charMax: string = "caratères maximum";
  telInvalid: string = "num téléphone invalide";
  zibCodeInvalid: string = "code postal invalide";
  emailInvalid: string = "email invalide";
  nbrCharMin0: number = 1;
  nbrCharMin1: number = 3;
  nbrCharMin2: number = 4;
  nbrCharMin3: number = 10;
  nbrCharMax1: number = 5;
  nbrCharMax2: number = 15;
  acceptedFileSize: number = 5000000;
  invalidFileSize: string = "fichier trop grand";
  invalidFileType: string = "type du fichier invalide";
  //new modif fin
  serviceMode: Observable<Structure>;
  messageServiceDetail: string = 'Structure bien ajoutée!';
  selectedContact: Acteur;
  //new modif  debut
  @ViewChild('conventionUrl') conventionUrl: FileUpload;
  ftpServerLogoUrl: string | ArrayBuffer | undefined | null = '';
  ftpServerCharteUrl: string;
  ftpServerConventionUrl: string;
  ftpServerPathFDLVDocs: string = 'fdlv_integ/FORUM/fdlv_documents/';
  ftpServerPathFDLVImage: string = 'fdlv_integ/FORUM/fdlv_images/';
  ftpServerPathOtherDocs: string = 'fdlv_integ/FORUM/str_documents/';
  ftpServerPathOtherImg: string = 'fdlv_integ/FORUM/str_images/';
  fileTypes: Array<string> = ['image/png', 'image/jpeg', 'image/jpj'];
  warnColorStyle: string = "color:red; font-weight: bold;";
  containerStyle: string = "border:solid 1px gray; padding:.2em";
  inputTextStyle1: string = `text-base text-color surface-overla p-2 border-1 border-solid 
  surface-border border-round appearance-none outline-none focus:border-primary w-full`;
  inputTextStyle2: string = `text-base text-color surface-overlay appearance-none outline-none focus:border-primary w-full`;
  //new modif fin
  selectedResponsabilitie: any;
  uploadedFiles: any[] = [];
  asterix: string = '*';
  //new modif debut
  width: number = 250;
  height: number = 250;
  niveauResponsabilites$: Observable<NiveauResponsabilite[]>;
  //new modif fin
  isLogoUploaded: boolean = false;
  isCharteUploaded: boolean = false;
  constructor(
    private router: Router,
    private messageService: MessageService,
    private cdr: ChangeDetectorRef,
    private fb: FormBuilder,
    private acteurService: ActeurService,
    private structureService: StructureService,
    private mediaResourceService: MediaResourceService,
    private confirmationService: ConfirmationService,
    private niveauResponsabiliteService: NiveauResponsabiliteService
  ) {

    //new modif debut
    this.structureForm = this.fb.group({
      id: [{ value: '', disabled: true }],
      code: ['', [Validators.required, Validators.minLength(this.nbrCharMin1)]],
      reference: [{ value: '', disabled: true }],
      libelle: ['', [Validators.required, Validators.minLength(this.nbrCharMin0)]],
      niveauResponsabilite: [''],
      logoDescription: ['', Validators.minLength(this.nbrCharMin3)],
      logoUrl: [''],
      charteDescription: ['', Validators.minLength(this.nbrCharMin3)],
      charteUrl: [''],
      adresse01: ['', Validators.minLength(this.nbrCharMin3)],
      adresse02: ['', Validators.minLength(this.nbrCharMin3)],
      cp: ['', [Validators.required, Validators.minLength(this.nbrCharMin2), Validators.maxLength(this.nbrCharMax1),
      Validators.pattern(this.zipCodePattern)],
      ],
      commune: ['', [Validators.required, Validators.minLength(this.nbrCharMin1)]],
      contact: [''],
      telAccueilStructure: ['', [Validators.required, Validators.minLength(this.nbrCharMin3), Validators.maxLength(this.nbrCharMax2),
      Validators.pattern(this.telPattern)]],
      emailAccueilStructure: ['', [Validators.email, Validators.required, Validators.pattern(this.emailPattern)]],
      commentaire: ['', Validators.minLength(this.nbrCharMin3)],
      conventionDescription: ['', Validators.minLength(this.nbrCharMin3)],
      conventionUrl: [''],
    });
    //new modif fin
  }

  ngOnInit(): void {
    //new modif debut
    this.contacts$ = this.acteurService.getAllActeurs().pipe(
      map((actors: Acteur[]) => {
        actors.forEach((actor: Acteur) => {
          actor.name = actor.id + ' '
            + actor.reference + ' '
            + actor.name + ' '
            + actor.lastName;
        })
        return actors;
      })
    );

    this.niveauResponsabilites$ = this.niveauResponsabiliteService.getAllResponsibilitiesLevel().pipe(
      map((nivResponsablites: NiveauResponsabilite[]) => {
        nivResponsablites.forEach((nivResp: NiveauResponsabilite) => {
          nivResp.libelle = nivResp.id + ' '
            + nivResp.responsibilityLevel + ' '
            + nivResp.libelle;
        });
        return nivResponsablites;
      })
    );
    //new modif fin
    if (window.history.state.type == 'modification') {
      this.modifMode = true;
      this.structure = window.history.state.data;
      this.initialisationFb();
    }
    this.cdr.detectChanges();
  }

  onRetour() {
    this.router.navigateByUrl('structure');
  }

  async onSave() {
    if (!this.structureForm.invalid) {
      //new modif debut
      this.confirmationService.confirm({
        acceptLabel: 'Oui',
        rejectLabel: 'Non',
        message: 'Voulez-vous confirmer?',
        header: 'Création structure ',
        icon: 'pi pi-exclamation-triangle',
        //new modif fin

        //new modif debut
        accept: () => {
          this.callServiceStructureAddOrUpdate().subscribe({
            next: (structureCreatedInit) => {
              this.structureForm.get('id').setValue(structureCreatedInit.id);
              this.messageService.add({
                key: 'save-sucess',
                severity: 'success',
                detail: this.messageServiceDetail,
                sticky: true
              });
              this.cdr.detectChanges();
              if (window.history.state.type == 'modification') {
                window.location.reload();
              }
              window.history.state.type = 'modification';
              window.history.state.data = this.structure;
              this.router.navigate(['newstructure'], { state: { data: this.structure, type: 'modification' } });
            },
            error: (error) => {
            },
          });
          //new modif fin     
        },
        reject: () => {
          this.messageService.add({
            key: 'save-rejected',
            severity: 'error',
            detail: 'la sauvegarde est annulée!',
            sticky: true
          });
        },
      });
    }
  }

  //new modif debut
  private callServiceStructureAddOrUpdate(): Observable<Structure> {
    let structureModel: Structure = this.structureForm.value;
    if (this.structureForm.get('niveauResponsabilite').value != null) {
      structureModel.niveauResponsabilite = this.structureForm.value.niveauResponsabilite.responsibilityLevel;
    }
    return new Observable((subscriber) => {
      if (this.modifMode === true) {
        this.messageServiceDetail = 'Structure bien modifiée!';
        this.serviceMode = this.structureService.updateStructure(
          this.structureForm.value.id, structureModel);
      } else {
        this.serviceMode = this.structureService.createStructure(structureModel);
      }

      this.serviceMode.subscribe({
        next: (structureCreated) => {
          subscriber.next(structureCreated);
        },
        error: (error) => {
          subscriber.error(error)
        },
      });
    });
  }
  //new modif fin


  onUpdate(structure: any) {
    this.onSave();
  }
  //new modif debut
  initialisationFb() {
    this.structureForm = this.fb.group({
      id: this.structure?.id,
      code: this.structure?.code,
      reference: this.structure?.reference,
      libelle: this.structure?.libelle,
      niveauResponsabilite: this.structure?.niveauResponsabilite,
      logoDescription: this.structure?.logoDescription,
      logoUrl: this.structure?.logoUrl,
      charteDescription: this.structure?.charteDescription,
      charteUrl: this.structure?.charteUrl,
      adresse01: this.structure?.adresse01,
      adresse02: this.structure?.adresse02,
      cp: this.structure?.cp,
      commune: this.structure?.commune,
      contact: this.structure?.contact,
      telAccueilStructure: this.structure?.telAccueilStructure,
      emailAccueilStructure: this.structure?.emailAccueilStructure,
      commentaire: this.structure?.commentaire,
      conventionDescription: this.structure?.conventionDescription,
      conventionUrl: this.structure?.conventionUrl
    });
    this.selectedContact = this.structure.contact;
    this.selectedResponsabilitie = this.structure.niveauResponsabilite;
    //new modif fin
    this.cdr.detectChanges();
  }

  //new modif debut
  isInvalidIdentiteStructure(): boolean {
    return (
      this.structureForm.get('code').invalid ||
      this.structureForm.get('libelle').invalid ||
      this.structureForm.get('cp').invalid ||
      this.structureForm.get('commune').invalid ||
      this.structureForm.get('telAccueilStructure').invalid ||
      this.structureForm.get('emailAccueilStructure').invalid ||
      this.structureForm.get('logoDescription').invalid ||
      this.structureForm.get('charteDescription').invalid ||
      this.structureForm.get('adresse01').invalid ||
      this.structureForm.get('adresse02').invalid ||
      this.structureForm.get('commentaire').invalid ||
      this.structureForm.get('conventionDescription').invalid
    );
  }

  /* updateSelectedContact(contact: Acteur) {
    this.selectedContact = contact;
  }
 */
  onResetModification() {
    window.location.reload();
  }
  //new modif fin

  onDateDebutChange() {
  }

  onDateRelance() { }

  //new modif debut

  onLogoSelect(): any {
    if (this.logoUrl && this.logoUrl.files.length > 0) {
      let file: File = this.logoUrl.files[0];
      let fileName: string = file.name;
      fileName = fileName.substring(0, fileName.lastIndexOf('.'));
      const formData = new FormData();
      formData.append('media', file);
      formData.append('name', file.lastModified + '-' + this.structureForm.value.code + '-' + fileName + '_logo');
      if (this.structureForm.value.code.toLocaleUpperCase() === 'FDLV') {
        formData.append('path', this.ftpServerPathFDLVImage);
      }
      else {
        formData.append('path', this.ftpServerPathOtherImg);
      }

      this.mediaResourceService.addPhoto(formData).subscribe((data: string) => {
        const parsedResponse = JSON.parse(data);
        this.ftpServerLogoUrl = parsedResponse.mediaUrl;
        this.structureForm.patchValue({
          logoUrl: this.ftpServerLogoUrl
        })
      });

    }
    else {
      return null;
    }
  }

  onCharteSelect(): any {

    if (this.charteUrl && this.charteUrl.files.length > 0) {
      const charteFile = this.charteUrl.files[0];
      let charteFilename: string = charteFile.name;
      charteFilename = charteFilename.substring(0, charteFilename.indexOf('.'));
      const formDataCharte = new FormData();
      formDataCharte.append('media', charteFile);
      formDataCharte.append('name', this.structureForm.value.code + '-' + charteFilename + '_charte');
      if (this.structureForm.value.code.toLocaleUpperCase() === 'FDLV' && this.fileTypes.includes(charteFile.type)) {
        formDataCharte.append('path', this.ftpServerPathFDLVImage);
      }
      else if (this.structureForm.value.code.toLocaleUpperCase() === 'FDLV' && charteFile.type === 'application/pdf') {
        formDataCharte.append('path', this.ftpServerPathFDLVDocs);
      }
      else if (this.structureForm.value.code.toLocaleUpperCase() !== 'FDLV' && this.fileTypes.includes(charteFile.type)) {
        formDataCharte.append('path', this.ftpServerPathOtherImg);
      }
      else if (this.structureForm.value.code.toLocaleUpperCase() !== 'FDLV' && charteFile.type === 'application/pdf') {
        formDataCharte.append('path', this.ftpServerPathOtherDocs);
      }

      this.mediaResourceService.addPhoto(formDataCharte).subscribe((response: string) => {
        const ftp: any = JSON.parse(response);
        this.ftpServerCharteUrl = ftp.mediaUrl;
        this.structureForm.patchValue({
          charteUrl: this.ftpServerCharteUrl
        })
      });
    }
    else {
      return null;
    }
  }
  onConventionSelect(): any {
    if (this.conventionUrl && this.conventionUrl.files.length > 0) {
      const conventionFile: File = this.conventionUrl.files[0];
      let convationFileName: string = conventionFile.name;
      convationFileName = convationFileName.substring(0, convationFileName.indexOf('.'))
      const fileFormDataConvention: FormData = new FormData();
      fileFormDataConvention.append('name', this.structureForm.value.code + '-' + convationFileName + '_convention');
      fileFormDataConvention.append('media', conventionFile);
      if (this.structureForm.value.code.toLocaleUpperCase() === 'FDLV') {
        fileFormDataConvention.append('path', this.ftpServerPathFDLVDocs);
      }
      else {
        fileFormDataConvention.append('path', this.ftpServerPathOtherDocs);
      }
      this.mediaResourceService.addPhoto(fileFormDataConvention).subscribe((response: string) => {
        const ftpUrl: any = JSON.parse(response);
        this.ftpServerConventionUrl = ftpUrl.mediaUrl;
        this.structureForm.patchValue({
          conventionUrl: this.ftpServerConventionUrl
        });
      });
    }
    else {
      return null;
    }
  }
  //new modif fin
}
