import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpResponse } from '@angular/common/http';
import { Validators, FormBuilder, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, finalize, map } from 'rxjs';
import { Event, IEvent } from 'src/app/model/event.model';
import { IStage } from 'src/app/model/stage.model';
import { ITheme } from 'src/app/model/theme.model';
import { StageService } from 'src/app/services/stage.service';
import { ThemeService } from 'src/app/services/webfdlv/theme.service';
import { EventService } from 'src/app/services/event.service';
import { CardModule } from 'primeng/card';
import { CalendarModule } from 'primeng/calendar';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { TimelineModule } from 'primeng/timeline';

interface Step {
  id: number;
  title: string;
  order: number;
}

@Component({
  selector: 'app-event-view',
  standalone: true,
  imports: [CommonModule, CardModule, ReactiveFormsModule, FormsModule, CalendarModule, ProgressSpinnerModule, TimelineModule],
  templateUrl: './event-view.component.html',
  styleUrls: ['./event-view.component.scss']
})
export class EventViewComponent implements OnInit {
  isSaving = false;
  required = 'Champs Obligatoire !';
  ckeditorDescription = '';
  configCkEditor = {
    toolbarGroups: [
      { name: 'basicstyles', groups: ['basicstyles', 'cleanup'] },
      { name: 'paragraph', groups: ['align'] },
    ],
    removeButtons: 'Subscript,Superscript,Anchor,Source,Table',
    height: '7em',
  };


  isLoading = true;
  startAtInit!: Date;
  endAtInit!: Date;
  themeInit!: ITheme;
  editForm = this.fb.group({
    id: [],
    label: ["", [Validators.required]],
    description: ["", [Validators.required]],
    theme: [this.themeInit, [Validators.required]],
    address: ["", [Validators.required]],
    zipCode: ["", [Validators.required]],
    city: ["", [Validators.required]],
    cityLogoUrl: [],
    startAt: [this.startAtInit, [Validators.required]],
    endAt: [this.endAtInit, [Validators.required]],
    labelFinParcours: [""],
    finParcoursPdf: [""],
    otherEvent: [false, [Validators.required]],
    validatedEvent: false,
    fixOrder: false,
    numParcours: null,
    usefulInformation: ["", [Validators.required]],
    eventTeaserUrl: [],
  });

  initialSteps: Step[] = [];

  selectedSteps: Step[] = [];
  stages?: IStage[];
  displayBasic = false;

  items: any[] = [];
  isStageUpdate = false;
  // items_: MenuItem[];
  themesSharedCollection: ITheme[] = [];
  isPublished!: boolean;
  constructor(
    protected eventService: EventService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    protected stageService: StageService,
    protected themeService: ThemeService,
    public router: Router
  ) {
    this.editForm.disable();
    if(window.history.state.data){
      const eventId = Number(window.history.state.data);
      this.eventService.find(eventId).subscribe(res =>{
        this.isPublished = res.body.validatedEvent;
      });
    }
    /*this.activatedRoute.data.subscribe(({ event }) => {
      this.isPublished = event.validatedEvent;
    });*/
  }

  ngOnInit(): void {
    this.loadRelationshipsOptions();
    this.loadEventStages();
  }

  save(): void {
    this.isSaving = true;
    const event = this.createFromForm();
    if (event.id !== undefined) {
      this.subscribeToSaveResponse(this.eventService.update(event));
    } else {
      this.subscribeToSaveResponse(this.eventService.create(event));
    }
  }

  publish(): void {
    this.isSaving = true;
    const event = this.createFromForm();
    this.isPublished = !this.isPublished;
    event.validatedEvent = this.isPublished;

    if (event.id) {
      this.subscribeToSaveResponse(this.eventService.update(event));
    } else {
      this.subscribeToSaveResponse(this.eventService.create(event));
    }
  }

  showBasicDialog(): void {
    this.displayBasic = true;
  }

  previousState(): void {
    window.history.back();
  }

  sortSequence(a?: IStage, b?: IStage): number {
    if (a?.sequence !== undefined && b?.sequence !== undefined) {
      return a.sequence - b.sequence;
    }
    return 0;
  }

  saveChoices(): void {
    this.items = [];
    this.selectedSteps.forEach(e => {
      if (e.title && e.id) {
        this.stageService.find(e.id).subscribe(
          (step: IStage) => {
            this.items.push({ label: e.title, data: step });
          },
          (error: Error) => {
            console.error(error);
          }
        );
      }
    });
    this.displayBasic = false;
  }
  // previousState(): void {
  //   window.history.back();
  // }
  edition(): void {
    console.warn('redirect');
  }
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      data => this.onSaveSuccess(data),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(data: any): void {
    // Parcourir les stages et leur attribuers le nouveau evenement
    this.items.forEach(e => {
      const stage = e.data;
      if (stage) {
        stage.event = data.body;
        this.subscribeToStageSaveResponse(this.stageService.update(stage));
      }
    });

    this.router.navigate(['list-event']);

    // this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(event: any): void {
    this.editForm.patchValue({
      id: event.id,
      label: event.label,
      description: event.description,
      theme: event.theme,
      address: event.address,
      zipCode: event.zipCode,
      city: event.city,
      cityLogoUrl: event.cityLogoUrl,
      startAt: event.startAt ? new Date(event.startAt) : null,
      endAt: event.endAt ? new Date(event.endAt) : null,
      labelFinParcours: event.labelFinParcours,
      finParcoursPdf: event.finParcoursPdf,
      otherEvent: event.otherEvent,
      fixOrder: event.fixOrder,
      validatedEvent: event.validatedEvent,
      numParcours: event.numParcours,
      eventTeaserUrl: event.eventTeaserUrl,
      usefulInformation: event.usefulInformation,
    });

    this.themesSharedCollection = this.themeService.addThemeToCollectionIfMissing(this.themesSharedCollection, event.theme);
    this.isLoading =false;
    console.log("hello theme ", this.themesSharedCollection)
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
      otherEvent: this.editForm.get(['otherEvent'])?.value,
      eventTeaserUrl: this.editForm.get(['eventTeaserUrl'])!.value,
      usefulInformation: this.editForm.get(['usefulInformation'])!.value,
    };
  }

  protected loadEventStages(): void {
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
      if(window.history.state.type== 'view'){ 
        const eventId = Number(window.history.state.data);
        this.eventService.find(eventId).subscribe(res =>{
        let event  = res.body;
        if (event) {
          this.updateForm(event);
          if (event.id!) {
            let eventDetails: IEvent[];
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
              }
            });
          }
        }
      })
      }
    });
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
