import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpResponse } from '@angular/common/http';
import { Validators, FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, finalize, map } from 'rxjs';
import { Event, IEvent } from 'src/app/model/event.model';
import { FdlvUsers } from 'src/app/model/fdlv-user.model';
import { LogoTeaser } from 'src/app/model/logo-teaser.model';
import { IStage } from 'src/app/model/stage.model';
import { ITheme, Theme } from 'src/app/model/theme.model';
import { CustomValidators } from 'src/app/pages/shared/custom-validators';
import { AnswerService } from 'src/app/services/answer.service';
import { EventService } from 'src/app/services/event.service';
import { LogoTeaserService } from 'src/app/services/logo-teaser.service';
import { QuestionService } from 'src/app/services/question.service';
import { QuizzService } from 'src/app/services/quizz.service';
import { StageService } from 'src/app/services/stage.service';
import { FdlvUserService } from 'src/app/services/webfdlv/fdlv-users.service';
import { ThemeService } from 'src/app/services/webfdlv/theme.service';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { DialogModule } from 'primeng/dialog';
import { PickListModule } from 'primeng/picklist';
import { StepCreateComponent } from '../../step-list/step-create/step-create.component';
import { CalendarModule } from 'primeng/calendar';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { AlertService } from 'src/app/shared/alert/alert.service';

@Component({
  selector: 'app-event-create',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule, DropdownModule, DialogModule, PickListModule,
    StepCreateComponent, FormsModule, ReactiveFormsModule, CalendarModule],
  templateUrl: './event-create.component.html',
  styleUrls: ['./event-create.component.scss']
})
export class EventCreateComponent implements OnInit {
  isCreate = false;
  isSaving = false;
  isAdmin = true;
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

  editForm = this.fb.group(
    {
      id: [],
      label: [null, [Validators.required]],
      description: [null, [Validators.required]],
      theme: [null, [Validators.required]],
      address: [null, [Validators.required]],
      zipCode: [null, [Validators.required]],
      city: [null, [Validators.required]],
      cityLogoUrl: [null],
      startAt: [null, [Validators.required]],
      endAt: [null, [Validators.required]],
      labelFinParcours: [null],
      finParcoursPdf: [null],
      otherEvent: [true, [Validators.required]],
      validatedEvent: false,
      fixOrder: false,
      eventTeaserUrl: [null],
      usefulInformation: [null],
      codeParcours: [null],
      evtDemo: [false],
      evtFcoDatePropose: [null],
      evtFcoDateValide: [null],
      numParcours: [null],
    },
    { validator: [CustomValidators.fromToDate('startAt', 'endAt')] }
  );

  stages?: IStage[];
  displayBasic = false;
  displayChoice = false;

  items: any[] = [];
  isStageUpdate = false;
  themesSharedCollection: ITheme[] = [];
  showSteps = false;
  showDuplicate = false;
  stageDuplicate: IStage = {};
  cityUrlBool = false;
  eventUrlBool = false;
  cityLogoUrl: any;
  cityLogoUrlN: any;
  eventTeaserUrl: any;
  eventTeaserUrlN: any;
  logos: LogoTeaser[] = [];
  teasers: LogoTeaser[] = [];
  fdlvUsers?: FdlvUsers[];
  isLoading = false;
  evtUserLogin: any;
  evtUserFirstName: any;
  evtUserLastName: any;
  eventCreatedDateNull: any;
  eventStageNotExist?: boolean;
  statutEvent: any;

  constructor(
    public eventService: EventService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    protected stageService: StageService,
    protected themeService: ThemeService,
    protected quizzService: QuizzService,
    protected questionService: QuestionService,
    protected answerService: AnswerService,
    protected logoTeaserService: LogoTeaserService,
    public router: Router,
    public fdlvUserService: FdlvUserService,
    private alertService: AlertService
  ) { }

  ngOnInit(): void {
    this.loadRelationshipsOptions();
    this.loadEventStages();
    this.logoTeaserService.findAll().subscribe((res: HttpResponse<LogoTeaser[]>) => {
      this.logos = res.body ? res.body.filter(elt => elt.typeMedia === 'Logo') : [];
      this.teasers = res.body ? res.body.filter(elt => elt.typeMedia === 'Teaser') : [];
    });
  }

  save(): void {
    this.isSaving = true;
    this.savingUpdateCreate();
  }

  savingUpdateCreate(): void {
    const event = this.createFromForm();

   
    event.theme = this.editForm.get('theme')!.value?.id ? this.editForm.get('theme')!.value :
    this.themesSharedCollection.filter(t => t.id === Number(this.editForm.get('theme')?.value))[0];
    console.log("hello theme av ", event.theme)
    // event.theme =  event.theme?.id ? event.theme : this.themesSharedCollection.filter(t => t.id === event.theme)[0] ;

    console.log("hello theme ", this.themesSharedCollection.filter(t => t.id === Number(this.editForm.get('theme')?.value))[0])
    
    if (event.id) {
      this.subscribeToSaveResponse(this.eventService.partialUpdateChoixOrganisateur(event));
    } else {

     // event.theme = this.themesSharedCollection.filter(t => t.id === Number(this.editForm.get('theme')?.value))[0];
      
      //this.getThemeByID(Number(this.editForm.get('theme')?.value));
      this.subscribeToSaveResponse(this.eventService.create(event));
      this.isCreate = true;
    }
  }

  protected getThemeByID(themeId?: number): ITheme {
    if (this.themesSharedCollection === undefined) {
      return { ...new Theme() };
    }
    return this.themesSharedCollection.filter(t => t.id === themeId)[0];
  }
  ChoiceValue(value: boolean): void {
    this.editForm.patchValue({
      validatedEvent: value,
    });
  }

  changeBoolCityUrl(): void {
    this.cityUrlBool = !this.cityUrlBool;
  }

  changeBoolTeaserUrl(): void {
    this.eventUrlBool = !this.eventUrlBool;
  }

  showSeletedSteps(): boolean {
    return (this.showSteps = !this.showSteps);
  }

  showBasicDialog(): void {
    this.displayBasic = true;
  }

  async saveChoices(): Promise<void> {
    this.items = [];
    let i = 1;

    for (let j = 0; j < this.eventService.selectedSteps.length; j++) {
      const e = this.eventService.selectedSteps[j];
      if (e.title && e.id) {
        const promiseStep = await this.stageService.findSync(e.id);
        const step = promiseStep.body;
        if (step != null) {
          step.sequence = i;
          i = i + 1;
        }
        //  step.challenge.description = step.challenge.description == null ? '' : step.challenge.description;
        this.stageService.update(step).subscribe(error => {
          console.log(error);
        });

        this.items.push({ label: e.title, data: step });
      }
    }
    this.displayBasic = false;
  }
  previousState(): void {
    window.location.href = '/event-list';
  }

  openStagePanel(): void {
    // this.displayBasic = false;
    this.eventService.displayStagePanel = true;
  }

  eraseModification(): void {
    window.location.reload();
  }


  loadEventStages(): void {
    this.eventService.initialSteps = [];
    this.eventService.selectedSteps = [];
    this.stageService.query().subscribe((res: HttpResponse<IStage[]>) => {
      this.stages = res.body ?? [];
      if (this.stages.length > 0) {
        this.stages.forEach(e => {
          if (!e.event?.id) {
            // à utiliser pour mettre uniquement les etapes qui ne sont pas dans les évenements
            this.eventService.initialSteps.push({ id: e.id!, title: e.label!, order: e.sequence! });
          }
        });
      }

      // this.activatedRoute.data.subscribe(({ event }) => {
      if (window.history.state.type == 'edit') {
        const eventId = Number(window.history.state.data);
        this.eventService.find(eventId).subscribe(res => {
          let event = res.body;
          if (event) {
            this.updateForm(event);
            if (event.id!) {
              let eventDetails: Event[];
              this.eventService.queryDetails().subscribe((ret: HttpResponse<IEvent[]>) => {
                eventDetails = ret.body ?? [];
                const currentEvent = eventDetails.find(e => e.id === event.id);
                if (currentEvent?.stages !== null) {
                  this.stages = currentEvent?.stages;
                  this.stages?.sort((a, b) => this.sortSequence(a, b)); // Trie les stages en fonction de la séquence
                  this.stages?.forEach(step => {
                    this.items.push({ label: step.label, data: step });
                    this.eventService.selectedSteps.push({ id: step.id!, title: step.label!, order: step.sequence! });
                    this.eventService.initialSteps = this.eventService.initialSteps.filter(item => item.id !== step.id);
                  });
                  // this.items.sort((a,b)=>this.sortSequence(a,b));
                }
              });
            }
          }
        });
      }
    });
    // this.eventService.initialSteps=[{id:1,title:"test",order:1}];

    console.warn('1-->', this.eventService.initialSteps);
    console.warn('2-->', this.eventService.selectedSteps);
  }

  sortSequence(a?: IStage, b?: IStage): number {
    if (a?.sequence !== undefined && b?.sequence !== undefined) {
      return a.sequence - b.sequence;
    }
    return 0;
  }
  loadStages(): void {
    this.eventService.initialSteps = [];
    this.eventService.selectedSteps = [];
    this.stageService.query().subscribe((res: HttpResponse<IStage[]>) => {
      this.stages = res.body ?? [];
      const initialList: any[] = [];
      if (this.stages.length > 0) {
        this.stages.forEach(e => {
          initialList.push({ id: e.id!, title: e.label!, order: e.sequence! });
        });
        this.eventService.initialSteps = JSON.parse(JSON.stringify(initialList));
      }
    });
  }

  async duplicate(id: number): Promise<void> {
    this.showDuplicate = false;
    for (let i = 0; i < this.items.length; i++) {
      if (this.items[i].data.id === id) {
        const stage = this.items[i].data;

        this.stageDuplicate.label = 'D-' + String(stage.id) + '-' + String(stage.label);
        this.stageDuplicate.sequence = stage.sequence;
        this.stageDuplicate.latitude = stage.latitude;
        this.stageDuplicate.longitude = stage.longitude;
        this.stageDuplicate.videoUrl = stage.videoUrl;
        this.stageDuplicate.event = stage.event;
        this.stageDuplicate.quizz = stage.quizz;
        if (stage.quizz) {
          const quizz = stage.quizz;
          // Create quizz and get id of the quizz
          const questions = quizz.questions;
          const oldIDQuizz = quizz.id;
          const oldQuizzLabel = quizz.label;
          delete quizz.id;
          quizz.label = 'D-' + String(oldIDQuizz) + '-' + String(quizz.label);
          const resultQuizz = await this.quizzService.createSync(quizz);
          const DQuizz = resultQuizz.body;
          this.stageDuplicate.quizz = DQuizz;
          stage.quizz.label = oldQuizzLabel;
          // Create question and get id of the question
          for (let a = 0; a < questions.length; a++) {
            questions[a].quizz = DQuizz;
            // const oldIDQuestion = questions[a].id;
            // const oldQuestionLabel = questions[a].label;
            const answers = questions[a].answers;
            delete questions[a].id;
            // questions[a].label = 'D-' + String(oldIDQuestion) + '-' + String(questions[a].label);
            const resultQuestion = await this.questionService.createSync(questions[a]);
            const DQuestion = resultQuestion.body;
            // questions[a].label = oldQuestionLabel;
            // Create Answer
            for (let b = 0; b < answers.length; b++) {
              answers[b].question = DQuestion;
              delete answers[b].id;
              //TODO anne
              //   await this.answerService.createSync(answers[b]);
            }
          }
        }
        // Create Challenge and get id of the challenge
        /* if (stage.challenge) {
           const challenge = stage.challenge;
           const oldIDChallenge = challenge.id;
           const oldChallengeLabel = challenge.label;
           delete challenge.id;
           challenge.label = 'D-' + String(oldIDChallenge) + '-' + String(challenge.label);
           const resultChallenge = await this.challengeService.createSync(challenge);
           this.stageDuplicate.challenge = resultChallenge.body;
           stage.challenge.label = oldChallengeLabel;
         }*/
        // Create Event
        await this.stageService.createSync(this.stageDuplicate);
      }
    }
    this.showDuplicate = true;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      data => {
        this.onSaveSuccess(data);
      },

      () => this.onSaveError()
    );
  }

  protected sortStage(): void {
    let blnPresent;
    this.stages?.forEach(s => {
      // stage contient les currentStage uniquement, sauf a la création ou elle contient tous les stages
      blnPresent = false;
      this.items.forEach(e => {
        if (s.id === e.data.id) {
          blnPresent = true;
          // TODO
          // exit;
        }
      });
      if (!blnPresent) {
        let promiseStep;
        this.stageService.find(s.id!).subscribe(x => {
          promiseStep = x;
          promiseStep.event = null;
          this.subscribeToStageSaveResponse(this.stageService.update(promiseStep));
        });
      }
    });
  }

  protected onSaveSuccess(data: any): void {
    if (!this.isCreate) {
      this.sortStage();
    }

    // Parcourir les stages et leur attribuers le nouveau evenement
    this.items.forEach(e => {
      let stage = e.data;
      if (stage) {
        const tempseq = stage.sequence;
        this.stageService.find(stage.id!).subscribe(x => {
          stage = x;
          x.event = data.body;
          x.sequence = tempseq;
          this.subscribeToStageSaveResponse(this.stageService.update(stage));
        });
      }
    });
    if (this.isCreate) {
      this.isCreate = false;
      this.previousState();
    }
    this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Parcours", detail:"La sauvegarde est effectuée."})
  }

  protected onSaveError(): void {
    // Api for inheritance.
    this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Parcours", detail:"Echec lors de la sauvegarde."})
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(event: IEvent): void {
    this.cityLogoUrlN = event.cityLogoUrl;
    this.eventTeaserUrlN = event.eventTeaserUrl;
    this.editForm.patchValue({
      id: event.id,
      label: event.label,
      description: event.description,
      theme: event.theme,
      address: event.address,
      evtFcoDatePropose: event.evtFcoDatePropose,
      evtFcoDateValide: event.evtFcoDateValide,
      zipCode: event.zipCode,
      city: event.city,
      cityLogoUrl: event.cityLogoUrl,
      startAt: event.startAt ? new Date(event.startAt) : null,
      endAt: event.endAt ? new Date(event.endAt) : null,
      labelFinParcours: event.labelFinParcours,
      finParcoursPdf: event.finParcoursPdf,
      otherEvent: event.otherEvent,
      validatedEvent: event.validatedEvent,
      fixOrder: event.fixOrder,
      eventTeaserUrl: event.eventTeaserUrl,
      usefulInformation: event.usefulInformation,
      codeParcours: event.codeParcours,
      evtDemo: event.evtDemo,
      evtUser: event.evtFcoId,
      numParcours: event.numParcours,
      evtCreatorID: event.evtFcoFusId,
    });
    this.themesSharedCollection = this.themeService.addThemeToCollectionIfMissing(this.themesSharedCollection, event.theme);
    this.eventStageNotExist = true;
    console.log('TRUEX', this.eventStageNotExist);
    this.stageService.query().subscribe(data => {
      data.body?.forEach(e => {
        if (e.event) {
          if (e.event.id) {
            if (e.event.id === event.id) {
              this.eventStageNotExist = false;
              console.log('TRUEX', this.eventStageNotExist);
            }
          }
        }
      });
    });

    // Message (statut) si évènement publié ou non
    if (event.validatedEvent === false) {
      this.statutEvent = '[Non Publié]';
    } else {
      this.statutEvent = '[Publié]';
    }

    // Récupération des informations sur l'organisateur
    if (event.evtFcoDatePropose === null || event.evtFcoDateValide === null) {
      this.eventCreatedDateNull = true;
    }
    if (event.evtFcoFusId) {
      this.fdlvUserService.find(event.evtFcoFusId).subscribe(data => {
        if (data.body !== null) {
          this.evtUserLogin = data.body.login;
          this.evtUserFirstName = data.body.prenom;
          this.evtUserLastName = data.body.nom;
          if (data.body.prenom && data.body.nom) {
            this.isAdmin = false;
          }
        }
      });
    }
  }

  get id(): number {
    return this.editForm.get(['id'])!.value as number;
  }

  protected createFromForm(): IEvent {
    return {
      ...new Event(),
      id: this.editForm.get(['id'])!.value,
      label: this.editForm.get(['label'])!.value,
      description: this.editForm.get(['description'])!.value,
      theme: this.editForm.get(['theme'])!.value,
      address: this.editForm.get(['address'])!.value,
      zipCode: this.editForm.get(['zipCode'])!.value,
      city: this.editForm.get(['city'])!.value,
      cityLogoUrl: this.editForm.get(['cityLogoUrl'])!.value,
      startAt: this.editForm.get(['startAt'])!.value,
      endAt: this.editForm.get(['endAt'])!.value,
      labelFinParcours: this.editForm.get(['labelFinParcours'])!.value,
      finParcoursPdf: this.editForm.get(['finParcoursPdf'])!.value,
      otherEvent: this.eventStageNotExist,
      validatedEvent: this.editForm.get(['validatedEvent'])!.value,
      fixOrder: this.editForm.get(['fixOrder'])!.value,
      numParcours: this.editForm.get(['numParcours'])!.value,
      eventTeaserUrl: this.editForm.get(['eventTeaserUrl'])!.value,
      usefulInformation: this.editForm.get(['usefulInformation'])!.value,
      codeParcours: this.editForm.get(['codeParcours'])!.value,
      evtDemo: this.editForm.get(['evtDemo'])!.value,
    };
  }

  protected subscribeToStageSaveResponse(result: Observable<HttpResponse<IStage>>): void {
    result.pipe(finalize(() => this.onSaveStageFinalize())).subscribe(
      () => this.onSaveStageSuccess(),
      () => this.onSaveStageError()
    );
  }

  protected onSaveStageFinalize(): void {
    this.isStageUpdate = false;
  }

  protected onSaveStageSuccess(): void {
    console.warn('update stages');
  }

  protected onSaveStageError(): void {
    // Api for inheritance.
  }

  protected loadRelationshipsOptions(): void {
    this.themeService
      .query()
      .pipe(map((res: HttpResponse<ITheme[]>) => res.body ?? []))
      .pipe(map((themes: ITheme[]) => this.themeService.addThemeToCollectionIfMissing(themes, this.editForm.get('theme')!.value)))
      .subscribe((themes: ITheme[]) => (this.themesSharedCollection = themes));
  }
}

