import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpResponse } from '@angular/common/http';
import { Validators, FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { map, Observable, finalize } from 'rxjs';
import { IEvent } from 'src/app/model/event.model';
import { IQuizz } from 'src/app/model/quizz.model';
import { IStage, Stage } from 'src/app/model/stage.model';
import { FdlvVideo, getTypeOfMedias } from 'src/app/model/webfdlv/fdlv-video.model';
import { EventService } from 'src/app/services/event.service';
import { QuizzService } from 'src/app/services/quizz.service';
import { StageService } from 'src/app/services/stage.service';
import { FdlvVideoService } from 'src/app/services/webfdlv/fdlv-video.service';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { TimelineModule } from 'primeng/timeline';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { AlertService } from 'src/app/shared/alert/alert.service';

@Component({
  selector: 'app-step-create',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule, TimelineModule,  DropdownModule, FormsModule, ReactiveFormsModule],
  templateUrl: './step-create.component.html',
  styleUrls: ['./step-create.component.scss']
})
export class StepCreateComponent implements OnInit {
  isSaving = false;
  isCreate = false;
  durationInSeconds = 5;
  isDMns: any;
  longitude: any;
  latitude: any;
  typeMedia: string | undefined = '.mp4'; // defined as default value for field
  fileName: any;
  file: File | undefined;
  url = false;
  videourlUse = '';
  eventsSharedCollection: IEvent[] = [];
  videosSharedCollection: FdlvVideo[] = [];
  videosSatisfyingTypeMediaSharedCollection: FdlvVideo[] = [];
  typeOfMedias: string[] = getTypeOfMedias();
  currentVideoQuizzesSharedCollection: IQuizz[] = [];
  allQuizzesSharedCollection: IQuizz[] = [];
  checked: boolean = true;

  editForm = this.fb.group({
    id: [],
    label: ["", [Validators.required]],
    sequence: [null],
    latitude: [null, [Validators.required]],
    longitude: [null, [Validators.required]],
    typeMedia: ['.mp4', Validators.required],
    videoUrl: [],
    event: [],
    quizz: [],
    challenge: [],
    stage_defi_video: [],
    stage_defi_partage: [],
    videoDescription: [],
    videoImageUrl: [],
    video: [],
  });
  @Output() stepListUpdated = new EventEmitter();
  customPatternDmnS = `^[0-9]+°[0-9]+'[0-9]+(.[0-9]+)?"$`;
  customPatternDecimal = `[0-9]+(.[0-9]+)?"$`;
  customPattern = this.customPatternDecimal;

  placeHolderDmnS = "xx°xx'xx.xx&quot;";
  placeHolderDecimal = 'xx.xx';
  placeHolder = this.placeHolderDecimal;

  initialValue = 't';

  finalQuiz: any;
  selectedURL = 'Sélectionner un média';
  constructor(
    protected stageService: StageService,
    protected eventService: EventService,
    protected _videoService: FdlvVideoService,
    protected quizzService: QuizzService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private router: Router,
    private alertService: AlertService
  ) { }

  ngOnInit(): void {
    /*if(window.history.state.type== 'edit'){ 
      const stage = window.history.state.data;
      this.loadRelationshipsOptions(stage);
    }*/

    this.loadRelationshipsCollections();
    if(window.history.state.type== 'edit'){ 
      const stage = window.history.state.data;
      if (stage) {
        this.updateForm(stage);
      }
    }
    
  }

  getFile(event: any): void {
    this.file = event.target.files[0];
  }

  save(): void {
    this.isSaving = true;
    const stage = this.createFromForm();
    if (stage.id) {
      this.subscribeToSaveResponse(this.stageService.update(stage));
    } else {
      this.subscribeToSaveResponse(this.stageService.create(stage));
      this.isCreate = true;
    }
  }

  duplicate(): void {
    this.isSaving = true;
    const stage = this.createFromForm();
    if (stage.id) {
      //   this.subscribeToSaveResponse(this.stageService.duplicateStep(stage));
      this.isCreate = true;
    }
  }

  changeBoolUrl(): void {
    this.url = !this.url;
  }

  trackEventById(index: number, item: IEvent): number {
    return item.id!;
  }

  trackQuizzById(index: number, item: IQuizz): number {
    return item.id!;
  }

  trackVideoUrlById(index: number, item: FdlvVideo): number {
    return item.id!;
  }
  trackVideoUrlByvideo_url(index: number, item: FdlvVideo): string {
    return item.urlVideo!;
  }

  trackvideoUrlById(index: number, item: FdlvVideo): number {
    return item.id!;
  }

  onChangeSelectedMedia(value: any): void {
    if (value) {
      this.currentVideoQuizzesSharedCollection = value.quizzs;
      this.quizzService.findByLabel('Fin de Parcours').subscribe(finalQuiz => {
        this.finalQuiz = finalQuiz.body;
        this.currentVideoQuizzesSharedCollection = this.quizzService.addQuizzToCollectionIfMissing(
          this.currentVideoQuizzesSharedCollection,
          this.finalQuiz
        );
      });
    }
  }

  onTypeMediaChange(): void {
    this.videosSatisfyingTypeMediaSharedCollection = this.videosSharedCollection.filter(video => video.typeMedia === this.typeMedia);
  }

  eraseModification(): void {
    window.location.reload();
  }


  onCancelCreation(): void {
    window.location.href = '/step-list';
  }

  conversionLocalisation(event: any): void {
    console.warn('Conversion');
    console.warn('check : ', event.target.checked);
    console.warn('check : ', this.isDMns);
    this.isDMns = event.target.checked;
    if (event.target.checked) {
      this.customPattern = this.customPatternDmnS;
      this.latitude = this.stageService.decimalToMinuteSeconde(this.latitude);
      this.longitude = this.stageService.decimalToMinuteSeconde(this.longitude);
    } else {
      this.latitude = this.stageService.minuteSecondeToDecimal(this.latitude);
      this.longitude = this.stageService.minuteSecondeToDecimal(this.longitude);
    }
  }
    loadRelationshipsOptions(stage?: IStage): void {
    this.loadRelationshipsCollections();
    if (stage) {
      this.updateForm(stage);
    }
  }

  loadRelationshipsCollections(): void {
    this.loadEvents();
    this.loadQuizzes();
    this.loadVideos();
  }

  loadEvents(): void {
    this.eventService
      .query()
      .pipe(
        map((res: HttpResponse<IEvent[]>) => res.body ?? []),
        map((events: IEvent[]) => this.eventService.addEventToCollectionIfMissing(events, this.editForm.get('event')!.value))
      )
      .subscribe((events: IEvent[]) => (this.eventsSharedCollection = events));
  }

  loadQuizzes(): void {
    this.quizzService.findAll().subscribe((quizzes: IQuizz[]) => {
      this.allQuizzesSharedCollection = quizzes;
      this.currentVideoQuizzesSharedCollection = quizzes;
    });
  }

  loadVideos(): void {
    this._videoService
      .findAll()
      .pipe(map((res: HttpResponse<FdlvVideo[]>) => res.body ?? []))
      .subscribe((videosUrls: FdlvVideo[]) => {
        this.videosSharedCollection = videosUrls;
        this.onTypeMediaChange();
      });
  }

  protected updateForm(stage: IStage): void {
    this.updateLocationValues(stage);
    this.updateTypeMediaValue(stage);
    this.updateVideoUrlValue(stage);
    this.updateFormValues(stage);
    this.updateEventsCollection(stage);
    this.updateQuizzesCollection(stage);
    //this.updateChallengesCollection(stage);
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStage>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    if (this.eventService.displayStagePanel) {
      this.stepListUpdated.emit(true);
      this.eventService.displayStagePanel = false;
    } else {
      if (this.isCreate) {
        this.isCreate = false;
        this.router.navigateByUrl('/step-list');
      }
    }
    this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Etape", detail:"La sauvegarde est effectuée."})
  }

  protected onSaveError(): void {
    // Api for inheritance.
    this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Etape", detail:"Echec de la sauvegarde."})

  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  getSequence(): number | any {
    if (this.id) {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-return
      return this.editForm.get(['sequence'])!.value;
    } else {
      return 1;
    }
  }

  protected createFromForm(): IStage {
    let decimalLatitude: any;
    let decimalLongitude: any;
    let video: string;
    if (this.isDMns) {
      decimalLatitude = this.stageService.minuteSecondeToDecimal(this.editForm.get(['latitude'])!.value);
      decimalLongitude = this.stageService.minuteSecondeToDecimal(this.editForm.get(['longitude'])!.value);
    } else {
      decimalLatitude = this.editForm.get(['latitude'])!.value;
      decimalLongitude = this.editForm.get(['longitude'])!.value;
    }
    if (!this.url) {
      const urlVideoInForm = this.editForm.get(['videoUrl'])!.value?.urlVideo;
      video = this.isNullish(urlVideoInForm) ? this.videourlUse : <string>urlVideoInForm;
    } else {
      video = this.editForm.get(['videoUrl'])!.value;
    }
    return {
      ...new Stage(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
      sequence: this.getSequence(),
      latitude: decimalLatitude,
      longitude: decimalLongitude,
      typeMedia: this.editForm.get(['typeMedia'])!.value,
      videoUrl: video,
      event: this.editForm.get(['event'])!.value,
      quizz: this.editForm.get(['quizz'])!.value,
      //  challenge: this.editForm.get(['challenge'])!.value,*/
      stage_defi_video: this.editForm.get(['stage_defi_video'])!.value ? this.editForm.get(['stage_defi_video'])!.value : '',
      stage_defi_partage: this.editForm.get(['stage_defi_partage'])!.value ? this.editForm.get(['stage_defi_partage'])!.value : '',
      videoDescription: this.editForm.get(['videoDescription'])!.value,
      videoImageUrl: this.editForm.get(['videoImageUrl'])!.value,
      video: this.editForm.get(['video'])!.value,
    };
  }

  private updateLocationValues(stage: IStage): void {
    if (this.isDMns) {
      this.latitude = this.stageService.decimalToMinuteSeconde(stage.latitude);
      this.longitude = this.stageService.decimalToMinuteSeconde(stage.longitude);
    } else {
      this.latitude = stage.latitude;
      this.longitude = stage.longitude;
    }
  }

  private updateTypeMediaValue(stage: IStage): void {
    this.typeMedia = stage.typeMedia;
  }

  private updateVideoUrlValue(stage: IStage): void {
    this.videourlUse = <string>stage.videoUrl;
    this.selectedURL = this.videourlUse;
  }

  private updateFormValues(stage: IStage): void {
    this.editForm.patchValue({
      id: stage.id,
      label: stage.label,
      sequence: stage.sequence,
      latitude: this.latitude,
      longitude: this.longitude,
      typeMedia: this.typeMedia,
      videoUrl: stage.videoUrl,
      event: stage.event,
      quizz: stage.quizz,
      /*challenge: stage.challenge,*/
      stage_defi_video: stage.stage_defi_video,
      stage_defi_partage: stage.stage_defi_partage,
      videoDescription: stage.videoDescription,
      videoImageUrl: stage.videoImageUrl,
      video: stage.video,
    });
  }

  private updateEventsCollection(stage: IStage): void {
    this.eventsSharedCollection = this.eventService.addEventToCollectionIfMissing(this.eventsSharedCollection, stage.event);
  }

  /**
   * Update quizzes collection for a given stage.
   * @param stage The stage to update quizzes collection for.
   */
  private updateQuizzesCollection(stage: IStage): void {
    // Get all quizzes from the quiz service.
    this.quizzService.findAll().subscribe((quizzes: IQuizz[]) => {
      // Check if the stage has a video linked to it.
      const videoUrl = stage.videoUrl;
      if (videoUrl) {
        // If the stage has a videoUrl, find the video using its URL.
        this._videoService.findByUrl(videoUrl).subscribe(video => {
          // const videoId = video.id;
          // If the video has quizzes linked to it, update the current quizzes shared collection with those quizzes.
          if (video.quizzs) {
            this.currentVideoQuizzesSharedCollection = video.quizzs;
            this.quizzService.findByLabel('Fin de Parcours').subscribe(finalQuiz => {
              this.finalQuiz = finalQuiz.body;
              this.currentVideoQuizzesSharedCollection = this.quizzService.addQuizzToCollectionIfMissing(
                this.currentVideoQuizzesSharedCollection,
                this.finalQuiz
              );
            });
          } else {
            // If the video does not have quizzes linked to it, update the current quizzes shared collection with all quizzes
            // and add the quiz linked to the stage if it exists.
            this.currentVideoQuizzesSharedCollection = this.quizzService.addQuizzToCollectionIfMissing(quizzes, stage.quizz);
          }
        });
      } else {
        // If the stage does not have a video linked to it, update the current quizzes shared collection with all quizzes
        // and add the quiz linked to the stage if it exists.
        this.currentVideoQuizzesSharedCollection = this.quizzService.addQuizzToCollectionIfMissing(quizzes, stage.quizz);
      }
    });
  }

  /*private updateChallengesCollection(stage: IStage): void {
     this.challengesSharedCollection = this.challengeService.addChallengeToCollectionIfMissing(
       this.challengesSharedCollection,
       stage.challenge
     );
   }*/

  private isNullish(input: any): boolean {
    return input === undefined || input === null || input === 'undefined' || input === 'null';
  }

  get id(): number {
    return this.editForm.get(['id'])!.value as number;
  }
}
