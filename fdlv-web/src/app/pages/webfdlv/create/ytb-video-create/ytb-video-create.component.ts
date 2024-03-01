import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpResponse } from '@angular/common/http';
import { IYtbVideo, YtbVideo } from 'src/app/model/webfdlv/ytb-video.model';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { YtbVideoService } from 'src/app/services/webfdlv/ytb-video.service';
import { ActivatedRoute } from '@angular/router';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import dayjs from 'dayjs';
import { SharedModule } from 'primeng/api';
import { CalendarModule } from 'primeng/calendar';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';

@Component({
  selector: 'app-ytb-video-create',
  standalone: true,
  imports: [CommonModule,SharedModule,
  FormsModule, ReactiveFormsModule, CardModule, ButtonModule,
  CalendarModule,],
  templateUrl: './ytb-video-create.component.html',
  styleUrls: ['./ytb-video-create.component.scss']
})
export class YtbVideoCreateComponent implements OnInit {
  isSaving = false;
  isCreate = false;
  required = 'Champs Obligatoire !';
  ytbVideo: any | undefined = {};
  ckeditorDescription = '';
  durationInSeconds = 5;
  dateDebut!: Date;
  configCkEditor = {
    toolbarGroups: [
      { name: 'basicstyles', groups: ['basicstyles', 'cleanup'] },
      { name: 'paragraph', groups: ['align'] },
    ],
    removeButtons: 'Subscript,Superscript,Anchor,Source,Table',
    height: '7em',
  };
  editForm = this.fb.group({
    id: [],
    ordreAffichage: [null, [Validators.required]],
    dateDebut: [this.dateDebut, [Validators.required]],
    dateFin: null,
    publie: null,
    urlVideo: ["", [Validators.required]],
    commentaire: [null],
  });

  displayBasic = false;

  constructor(
    protected ytbVideoService: YtbVideoService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private alertService:AlertService
  ) {}

  ngOnInit(): void {
    if(window.history.state.type== 'edit'){ 
      let videoToBeEdited = window.history.state.data
      
         this.ytbVideoService.find(videoToBeEdited.id).subscribe(video => {
       this.ytbVideo = video?.body;
        this.updateForm();
        
      });

    }
    
  }

  formatedDate(date:Date){
    return dayjs(date).format('DD/MM/YYYY');
  }

  previousState(): void {
    window.location.href = '/ytb-video-list';
  }

  eraseModification(): void {
    window.location.reload();
  }

  
  save(): void {
    this.isSaving = true;
    const ytbVideo = this.createFromForm();
    if (ytbVideo.id) {
      this.subscribeToSaveResponse(this.ytbVideoService.update(ytbVideo));
    } else {
      const video= this.subscribeToSaveResponse(this.ytbVideoService.create(ytbVideo));
      this.isCreate = true;
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IYtbVideo>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    if (this.isCreate) {
      this.isCreate = false;
    }
    this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Video", detail:"La sauvegarde est effectuée."})
  
  }

  protected onSaveError(): void {
    this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Video", detail:"Echec lors de la modification."})

  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(): void {
    this.editForm.patchValue({
      id: this.ytbVideo.id,
      ordreAffichage: this.ytbVideo.ordreAffichage,
      dateDebut: this.ytbVideo?.dateDebut ? new Date(this.ytbVideo?.dateDebut) : null, 
      dateFin: this.ytbVideo.dateFin ? new Date(this.ytbVideo?.dateFin) : null,
      publie: this.ytbVideo.publie,
      urlVideo: this.ytbVideo.urlVideo,
      commentaire: this.ytbVideo.commentaire,
    });
  }

  protected createFromForm(): IYtbVideo {
    return {
      ...new YtbVideo(),
      id: this.editForm.get(['id'])!.value,
      ordreAffichage: this.editForm.get(['ordreAffichage'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value,
      dateFin: this.editForm.get(['dateFin'])!.value,
      publie: this.editForm.get(['publie'])!.value,
      urlVideo: this.editForm.get(['urlVideo'])!.value,
      commentaire: this.editForm.get(['commentaire'])!.value,
    };
  }

  get id(): number {
    return this.editForm.get(['id'])!.value as number;
  }
}

