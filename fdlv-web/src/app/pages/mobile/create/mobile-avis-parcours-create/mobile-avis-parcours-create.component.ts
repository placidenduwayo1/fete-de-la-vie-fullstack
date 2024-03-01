import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MobileAvisParcours } from 'src/app/model/mobile-avis-parcours.model';
import { HttpResponse } from '@angular/common/http';
import { Validators, FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, finalize } from 'rxjs';
import { Account } from 'src/app/core/auth/account.model';
import { MobileAvisParcoursService } from 'src/app/services/mobile/mobile-avis-parcours.service';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { CalendarModule } from 'primeng/calendar';

@Component({
  selector: 'app-mobile-avis-parcours-create',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule,CardModule, ButtonModule, CalendarModule],
  templateUrl: './mobile-avis-parcours-create.component.html',
  styleUrls: ['./mobile-avis-parcours-create.component.scss']
})
export class MobileAvisParcoursCreateComponent implements OnInit {
  account!: Account;
  isSaving = false;
  isCreate = false;
  durationInSeconds = 5;
  required = 'Champs Obligatoire !';

  eventIdInit!: number;
  mobileUserIdInit!: number;
  questionFinParcoursIdInit!: number;
  dateInit!: Date;
  editForm = this.fb.group({
    id: [],
    questionFinParcoursId: [this.questionFinParcoursIdInit, [Validators.required]],
    mobileUserId: [this.mobileUserIdInit, [Validators.required]],
    eventId: [this.eventIdInit, [Validators.required]],
    question: ["", [Validators.required]],
    avis: ["", [Validators.required]],
    date: [{value:this.dateInit, disabled:true}],
  });

  constructor(
    protected mobileAvisParcoursService: MobileAvisParcoursService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private alertService: AlertService,
    public router: Router
  ) {}

  ngOnInit(): void {
    if(window.history.state.type== 'edit'){ 
      const res = window.history.state.data;
      this.updateForm(res);
    }
  }

  previousState(): void {
    window.location.href = '/mobile-avis-list';
  }


  save(): void {
    this.isSaving = true;
    const mobileAvisParcours = this.createFromForm();
    if (mobileAvisParcours.id) {
      this.subscribeToSaveResponse(this.mobileAvisParcoursService.update(mobileAvisParcours));
    } else {
      this.subscribeToSaveResponse(this.mobileAvisParcoursService.create(mobileAvisParcours));
      this.isCreate = true;
    }
  }

  onCancelCreation(): void {
    window.location.href = '/mobile-avis-list';
  }

  eraseModification(): void {
    window.location.reload();
  }

  get id(): number {
    return this.editForm.get(['id'])!.value as number;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<MobileAvisParcours>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    if (this.isCreate) {
      this.isCreate = false;
    }
    this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Avis parcours", detail:"La sauvegarde est effectuée."})
  }

  protected onSaveError(): void {
    // Api for inheritance.
    this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Avis parcours", detail:"Echec de la sauvegarde."})

  }
  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(mobileAvisParcours: MobileAvisParcours |any): void {
    this.editForm.patchValue({
      id: mobileAvisParcours.id,
      questionFinParcoursId: mobileAvisParcours.questionFinParcoursId,
      mobileUserId: mobileAvisParcours.mobileUserId,
      eventId: mobileAvisParcours.eventId,
      question: mobileAvisParcours.question,
      avis: mobileAvisParcours.avis,
      date: mobileAvisParcours.date,
    });
  }

  protected createFromForm(): MobileAvisParcours {
    return {
      ...new MobileAvisParcours(),

      id: this.editForm.get(['id'])!.value,
      questionFinParcoursId: this.editForm.get(['questionFinParcoursId'])!.value,
      mobileUserId: this.editForm.get(['mobileUserId'])!.value,
      eventId: this.editForm.get(['eventId'])!.value,
      question: this.editForm.get(['question'])!.value,
      avis: this.editForm.get(['avis'])!.value,
      date: this.editForm.get(['date'])!.value,
    };
  }
}
