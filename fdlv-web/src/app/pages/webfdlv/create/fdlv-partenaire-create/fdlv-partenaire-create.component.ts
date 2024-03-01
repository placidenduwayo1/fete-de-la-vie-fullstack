import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { finalize } from 'rxjs/operators';
import { PartenaireService } from '../../../../services/webfdlv/partenaire.service';
import { IPartenaire, Partenaire } from '../../../../model/partenaire.model';
import { Priorite, Priorites } from '../../../../model/priorite.model';
import { ModulePartenaire, ModulePartenaires } from '../../../../model/partenaire-module.model';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TagModule } from 'primeng/tag';
import { DialogModule } from 'primeng/dialog';
import { FileUploadModule } from 'primeng/fileupload';
import { InputMaskModule } from 'primeng/inputmask';
import { InputTextModule } from 'primeng/inputtext';
import { MessageModule } from 'primeng/message';
import { PanelModule } from 'primeng/panel';
import { PickListModule } from 'primeng/picklist';
import { SelectButtonModule } from 'primeng/selectbutton';
import { TimelineModule } from 'primeng/timeline';
import { ToastModule } from 'primeng/toast';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { MediaResourceService } from 'src/app/services/forum/media-resource.service';

@Component({
  selector: 'gmd-fdlv-partenaire-create',
  templateUrl: './fdlv-partenaire-create.component.html',
  styleUrls: ['./fdlv-partenaire-create.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    ButtonModule,
    FormsModule,
    CardModule,
    InputMaskModule,
    ToastModule,
    SelectButtonModule,
    DialogModule,
    PickListModule,
    PanelModule,
    TimelineModule,
    ReactiveFormsModule,
    InputTextModule,
    TagModule,
    FileUploadModule,
    MessageModule

  ],
})
export class FdlvPartenaireCreateComponent implements OnInit {
  isSaving = false;
  durationInSeconds = 5;
  isCreate = false;
  required = 'Champs Obligatoire !';
  url: string | ArrayBuffer | undefined | null = '';
  fileToUpload: File | null = null;
  priorites: Priorite[] = Priorites;
  modulepartenaires: ModulePartenaire[] = ModulePartenaires;
  isInvalid = true;
  editForm = this.fb.group({
    id: [{value: null, disabled: true}],
    num_ordre: [null, [Validators.required]],
    image: null,
    priorite: ["", [Validators.required]],
    module:["", [Validators.required]],
  });

  constructor(
    protected partenaireService: PartenaireService,
    protected fb: FormBuilder,
    protected activatedRoute: ActivatedRoute,
    private alertService:AlertService,
    private mediaResourceService: MediaResourceService,
    private route : Router
  ) {}

  ngOnInit(): void {
    
    if(window.history.state.type== 'edit'){ 
     const partenaireId = Number(window.history.state.data);
      this.partenaireService.find(partenaireId).subscribe(partenaire => {
        if (partenaire.body?.image) {
          this.url = partenaire.body.image;
        }
        this.updateForm(partenaire.body!);
      });
    }
  }

  updateForm(partenaire: IPartenaire): void {
    this.editForm.patchValue({
      id: partenaire.id,
      num_ordre: partenaire.num_ordre,
      priorite: this.priorites.find((p: Priorite) => partenaire.priorite?.dbValue === p.dbValue)?.enumName,
      module: this.modulepartenaires.find((mp: ModulePartenaire) => partenaire.module?.dbValue === mp.dbValue)?.enumName,
      image: partenaire.image,
    });
  }

  save(): void {
    this.isSaving = true;
    const partenaire = this.createFromForm();
    
    const formData = new FormData();
    formData.append('media',  this.fileToUpload);
    formData.append('path', 'partenaires/');
    if (partenaire.id) {
      if (this.fileToUpload) {
        formData.append('name', this.getFileName());
      this.mediaResourceService.addPhoto(formData).subscribe((result) => {      
          const parsedResponse = JSON.parse(result); 
          partenaire.image = parsedResponse.mediaUrl ?? '';
          this.subscribeToSaveResponse(this.partenaireService.update(partenaire));
        });
      } else {
        this.subscribeToSaveResponse(this.partenaireService.update(partenaire));
      }
    } else {
      if (this.fileToUpload) {
        formData.append('name', this.getFileName());
        this.mediaResourceService.addPhoto(formData).subscribe((result) => {      
          const parsedResponse = JSON.parse(result); 
          partenaire.image = parsedResponse.mediaUrl ?? '';
          this.subscribeToSaveResponse(this.partenaireService.create(partenaire));
        });
      } else {
        this.subscribeToSaveResponse(this.partenaireService.create(partenaire));
      }
      this.isCreate = true;
    }
  }

  getFileName(){
    let fileName = this.fileToUpload.name;
    const lastIndex = fileName.lastIndexOf('.');
    const extension =  fileName.substring(lastIndex,  fileName.length);
    return fileName.replace(extension, "");
  }
 
  previousState(): void {
    window.location.href = '/partenaires-list';
  }
  eraseModification(): void {
    window.location.reload();
  }
  onCancelCreation(): void {
   window.location.href = '/partenaires-list';
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartenaire>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error:() => this.onSaveError()
      }
    );
  }
  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected onSaveSuccess(): void {
    if (this.isCreate) {
      // si création retour sur la list afin d'éviter un bug qui a chaque modification sur la page de création enregistre un autre quizz
      this.isCreate = false;
    }
    this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Partenaire", detail:"La sauvegarde est effectuée."})
  }

  protected onSaveError(): void {
    this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Partenaire", detail:"Echec de la sauvegarde."})
  }

  protected createFromForm(): IPartenaire {
    return {
      ...new Partenaire(),
      id: this.editForm.get(['id'])?.value,
      num_ordre: this.editForm.get(['num_ordre'])?.value,
      image: this.editForm.get(['image'])?.value,
      priorite: this.editForm.get(['priorite'])?.value,
      module: this.editForm.get(['module'])?.value,
    };
  }

  get id(): number {
    return this.editForm.get(['id'])!.value as number;
  }
}
