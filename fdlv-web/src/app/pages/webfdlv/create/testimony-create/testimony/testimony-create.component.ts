import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, FormsModule, Validators, FormGroup, ReactiveFormsModule,} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import { ButtonModule } from 'primeng/button';
import { CommonModule } from '@angular/common';
import { CardModule } from 'primeng/card';
import { MessageModule } from 'primeng/message';
import { MessageService } from 'primeng/api';
import { InputMaskModule } from 'primeng/inputmask';
import { ToastModule } from 'primeng/toast';
import { CalendarModule } from 'primeng/calendar';
import { CheckboxModule } from 'primeng/checkbox';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { DialogModule } from 'primeng/dialog';
import { FileUploadModule } from 'primeng/fileupload';
import { InputTextModule } from 'primeng/inputtext';
import { PanelModule } from 'primeng/panel';
import { PickListModule } from 'primeng/picklist';
import { TimelineModule } from 'primeng/timeline';
import { TagModule } from 'primeng/tag';
import { Testimony } from '../../../../../model/webfdlv/testimony.model';
import { TestimonyService } from '../../../../../services/webfdlv/testimony.service';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { MediaResourceService } from 'src/app/services/forum/media-resource.service';

@Component({
  selector: 'fdlv-testimony-update',
  templateUrl: './testimony-create.component.html',
  styleUrls: ['./testimony-create.component.scss'],
  standalone: true,
  imports:[
    CommonModule,
    ButtonModule,
    FormsModule,
    ReactiveFormsModule,
    CardModule,
  MessageModule,
    InputMaskModule,
    ToastModule,
    CalendarModule,
    CheckboxModule,
    InputTextareaModule,
    DialogModule,
    PickListModule,
    PanelModule,
    TimelineModule,
    InputTextModule,
    TagModule,
   FileUploadModule,
  ],
  providers: [MessageService],
})

export class TestimonyCreateComponent implements OnInit {
  isSaving = false;
  isCreate = false;
  testimonyToBeEdited: any | undefined = {};
  dateDebutInit!: Date;
  dateFinInit!: Date;
  testimony = {
    ...new Testimony(),
  };
  required = 'Champs Obligatoire !';
  ckeditorDescription = '';
  durationInSeconds = 5;
  configCkEditor = {
    toolbarGroups: [
      { name: 'basicstyles', groups: ['basicstyles', 'cleanup'] },
      { name: 'paragraph', groups: ['align'] },
    ],
    removeButtons: 'Subscript,Superscript,Anchor,Source,Table',
    height: '7em',
  };

  editForm: FormGroup = this.fb.group({
    id: [],
    ordreAffichage: [null,[Validators.required]],
    dateDebut: [this.dateDebutInit, [Validators.required]],
    dateFin: null,
    publie: null,
    auteur: ["", [Validators.required]],
    compagnie: [null],
    domaineMetier: [null],
    texteTemoignage: ["", [Validators.required]],
    lienImageAuteur: [null],
    commentaire: [null],
  });
  fileToUpload: File | null = null;
  displayBasic = false;
  url: string | ArrayBuffer | undefined | null = '';

  constructor(
    protected testimonyService: TestimonyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private alertService:AlertService,
    private mediaResourceService: MediaResourceService
  ) {}

  
  ngOnInit(): void {
    if(window.history.state.type== 'edit'){ 
    this.relaod();
    }
  }
  relaod(){
    let testimonyId = window.history.state.data
    this.testimonyService.find(testimonyId).subscribe(response =>{
      this.testimonyToBeEdited = response.body;
      if (this.testimonyToBeEdited.lienImageAuteur) {
        this.url = this.testimonyToBeEdited.lienImageAuteur;
      }
      this.updateForm(this.testimonyToBeEdited);
      
    })
  }

  ngOnChange(): void {
    this.testimony = {
      id: this.editForm.get(['id'])!.value,
      ordreAffichage: this.editForm.get(['ordreAffichage'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value,
      dateFin: this.editForm.get(['dateFin'])!.value,
      publie: this.editForm.get(['publie'])!.value,
      auteur: this.editForm.get(['auteur'])!.value,
      compagnie: this.editForm.get(['compagnie'])!.value,
      domaineMetier: this.editForm.get(['domaineMetier'])!.value,
      texteTemoignage: this.editForm.get(['texteTemoignage'])!.value,
      lienImageAuteur: this.editForm.get(['lienImageAuteur'])!.value,
      commentaire: this.editForm.get(['commentaire'])!.value,
    };
  }

  previousState(): void {
    window.location.href = '/testimony-list';
  }

  eraseModification(): void {
    window.location.reload();
  }



  save(): void {
    this.isSaving = true;
    const testimony = this.createFromForm();
    const formData = new FormData();
    formData.append('media', this.fileToUpload);
    formData.append('path', 'fdlv/testimony-img/');
    formData.append('name', testimony.auteur!);
    if (testimony.id) {
      if (this.fileToUpload) {
          this.mediaResourceService.addPhoto(formData)
          .subscribe((result) => {     
            const parsedResponse = JSON.parse(result); 
            testimony.lienImageAuteur = parsedResponse.mediaUrl;
            this.subscribeToSaveResponse(this.testimonyService.update(testimony));
          })
      } else {
        this.subscribeToSaveResponse(this.testimonyService.update(testimony));
      }
    } else {
      if (this.fileToUpload) {
        this.mediaResourceService.addPhoto(formData)
        .subscribe((result) => {   
          const parsedResponse = JSON.parse(result); 
          testimony.lienImageAuteur = parsedResponse.mediaUrl;   
          this.subscribeToSaveResponse(this.testimonyService.create(testimony));
        })
      } else {
        this.subscribeToSaveResponse(this.testimonyService.create(testimony));
        
      }
      this.isCreate = true;
    }
  }

  handleFileInput(input: EventTarget): void {
    this.fileToUpload = (<HTMLInputElement>input).files![0];

    // Preview image display
    const reader = new FileReader();
    reader.readAsDataURL(this.fileToUpload);
    reader.onload = event => {
      this.url = event.target!.result;
    };
  }

  getSubtitle(domaineMetier: string | null, compagnie: string | null): string {
    domaineMetier = domaineMetier === ' ' ? null : domaineMetier;
    compagnie = compagnie === ' ' ? null : compagnie;
    return [domaineMetier, compagnie].filter(Boolean).join(', ');
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<Testimony>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    if (this.isCreate) {
      this.isCreate = false;
    }
    this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Témoignage", detail:"La sauvegarde est effectuée."})
   
    
  }

  protected onSaveError(): void {
    // Api for inheritance.
    this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Témoignage", detail:"Echec lors de la modification."})
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(testimony: any): void {
    this.editForm.patchValue({
      id: testimony.id,
      ordreAffichage: testimony.ordreAffichage,
      dateDebut: testimony?.dateDebut ? new Date(testimony?.dateDebut) : null, 
      dateFin: testimony.dateFin ? new Date(testimony?.dateFin) : null,
      publie: testimony.publie,
      auteur: testimony.auteur,
      compagnie: testimony.compagnie,
      domaineMetier: testimony.domaineMetier,
      texteTemoignage: testimony.texteTemoignage,
      lienImageAuteur: testimony.lienImageAuteur,
      commentaire: testimony.commentaire,
    });
  }

  protected createFromForm(): Testimony {
    return {
      ...new Testimony(),
      id: this.editForm.get(['id'])!.value,
      ordreAffichage: this.editForm.get(['ordreAffichage'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value,
      dateFin: this.editForm.get(['dateFin'])!.value,
      publie: this.editForm.get(['publie'])!.value,
      auteur: this.editForm.get(['auteur'])!.value,
      compagnie: this.editForm.get(['compagnie'])!.value,
      domaineMetier: this.editForm.get(['domaineMetier'])!.value,
      texteTemoignage: this.editForm.get(['texteTemoignage'])!.value,
      commentaire: this.editForm.get(['commentaire'])!.value,
     lienImageAuteur: this.url,
    };
  }

  get id(): number {
    return this.editForm.get(['id'])!.value as number;
  }
}
