import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpResponse } from '@angular/common/http';
import { Validators, FormBuilder, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, finalize } from 'rxjs';
import { Account } from 'src/app/core/auth/account.model';
import { MobileQuestionFinParcours } from 'src/app/model/mobile-qfin-parcours.model';
import { MobileQfinParcoursService } from 'src/app/services/mobile/mobile-qfin-parcours.service';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { TypeQuestion, TypeQuestions } from 'src/app/model/type-question.modele';

@Component({
  selector: 'app-mobile-qfin-parcours-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, CardModule, ButtonModule],
  templateUrl: './mobile-qfin-parcours-create.component.html',
  styleUrls: ['./mobile-qfin-parcours-create.component.scss']
})
export class MobileQfinParcoursCreateComponent implements OnInit {
  account!: Account;
  isSaving = false;
  isCreate = false;
  durationInSeconds = 5;
  required = 'Champs Obligatoire !';
  numQuestionInit!: number;
  typeQuestions: TypeQuestion[] = TypeQuestions;

  editForm = this.fb.group({
    id: [],
    texte: ['', [Validators.required]],
    numQuestion: [this.numQuestionInit, [Validators.required]],
    question: ['', [Validators.required]],
    questionActive: [null],
    typeQuestion:["", Validators.required]
  });

  constructor(
    protected mobileQuestionFinParcoursService: MobileQfinParcoursService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private alertService: AlertService,
    public router: Router
  ) {}

  ngOnInit(): void {
    if(window.history.state.type== 'edit'){ 
      const mobileQfinParcours = window.history.state.data;
      this.updateForm(mobileQfinParcours);
  }
  }

  previousState(): void {
    window.location.href = '/mobile-qfin-list';
  }


  save(): void {
    this.isSaving = true;
    const mobileQuestionFinParcours = this.createFromForm();
    if (mobileQuestionFinParcours.id) {
      this.subscribeToSaveResponse(this.mobileQuestionFinParcoursService.update(mobileQuestionFinParcours));
    } else {
      this.subscribeToSaveResponse(this.mobileQuestionFinParcoursService.create(mobileQuestionFinParcours));
      this.isCreate = true;
    }
  }

  onCancelCreation(): void {
    window.location.href = '/mobile-qfin-list';
  }

  eraseModification(): void {
    window.location.reload();
  }

  get id(): number {
    return this.editForm.get(['id'])!.value as number;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<MobileQuestionFinParcours>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    if (this.isCreate) {
      this.isCreate = false;
    }
    this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Question de parcours", detail:"La sauvegarde est effectuée."})
  }

  protected onSaveError(): void {
    // Api for inheritance.
    this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Question de parcours", detail:"Echec de la sauvegarde."})

  }


  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(mobileQuestionFinParcours: MobileQuestionFinParcours): void {
    this.editForm.patchValue({
      id: mobileQuestionFinParcours.id,
      texte: mobileQuestionFinParcours.texte,
      numQuestion: mobileQuestionFinParcours.numQuestion,
      question: mobileQuestionFinParcours.question,
      questionActive: mobileQuestionFinParcours.questionActive,
      typeQuestion:  mobileQuestionFinParcours.typeQuestion 
    });
  }

  protected createFromForm(): MobileQuestionFinParcours {
    return {
      ...new MobileQuestionFinParcours(),

      id: this.editForm.get(['id'])!.value,
      texte: this.editForm.get(['texte'])!.value,
      numQuestion: this.editForm.get(['numQuestion'])!.value,
      question: this.editForm.get(['question'])!.value,
      questionActive: this.editForm.get(['questionActive'])!.value,
      typeQuestion: this.editForm.get(['typeQuestion'])!.value
    };
  }
}
