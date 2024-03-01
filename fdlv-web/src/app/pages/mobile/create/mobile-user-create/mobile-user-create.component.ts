import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MobileUser } from 'src/app/model/mobile-user.model';
import { TrancheAge, TrancheAges } from 'src/app/model/tranche.age.model';
import { Sexe, Sexes } from 'src/app/model/sexe.model';
import { Observable, finalize, first } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { Validators, FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Account } from 'src/app/core/auth/account.model';
import { AccountService } from 'src/app/pages/account/account.service';
import { MobileUserService } from 'src/app/services/mobile/mobile-users.service';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { CalendarModule } from 'primeng/calendar';

@Component({
  selector: 'app-mobile-user-create',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule, FormsModule, ReactiveFormsModule, CalendarModule],
  templateUrl: './mobile-user-create.component.html',
  styleUrls: ['./mobile-user-create.component.scss']
})
export class MobileUserCreateComponent implements OnInit {
  sexesDropdown: Sexe[] = Sexes;
  agesDropdown: TrancheAge[] = TrancheAges;
  account!: Account;
  isSaving = false;
  isCreate = false;
  durationInSeconds = 5;
  required = 'Champs Obligatoire !';
  myDate = new Date();
  dateInit!: Date;
  editForm = this.fb.group({
    id: [],
    pseudo: ['', [Validators.required]],
    sexe: [null],
    trancheAge: [null],
    dateFermeture: [this.dateInit],
  });

  constructor(
    protected mobileUserService: MobileUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private accountService: AccountService,
    public router: Router,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    if(window.history.state.type== 'edit'){ 
      const id = window.history.state.data;
      this.mobileUserService.find(id).subscribe(res =>{
        this.updateForm(res.body);
      })
    }

    // Récupération de l'utilisateur afin de savoir qui à creer l'organisateur
    this.accountService.getAccount().pipe(first()).subscribe({
      next: (response: Account) => this.account = response
    });
  }

  previousState(): void {
    window.location.href = '/mobile-user-list';
  }



  save(): void {
    this.isSaving = true;
    const mobileUser = this.createFromForm();
    if (mobileUser.id) {
      this.subscribeToSaveResponse(this.mobileUserService.update(mobileUser));
    } else {
      this.subscribeToSaveResponse(this.mobileUserService.create(mobileUser));
      this.isCreate = true;
    }
  }

  onCancelCreation(): void {
    window.location.href = '/mobile-user-list';
  }

  eraseModification(): void {
    window.location.reload();
  }

  get id(): number {
    return this.editForm.get(['id'])!.value as number;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<MobileUser>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    if (this.isCreate) {
      this.isCreate = false;
    }
    this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Utilisateur mobile", detail:"La sauvegarde est effectuée."})
  }

  protected onSaveError(): void {
    // Api for inheritance.
    this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Utilisateur mobile", detail:"Echec de la sauvegarde."})

  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(mobileUser: MobileUser | any): void {
    this.editForm.patchValue({
      id: mobileUser.id,
      pseudo: mobileUser.pseudo,
      sexe: this.sexesDropdown.find((s: Sexe) => mobileUser.sexe?.dbValue === s.dbValue)?.enumName,
      trancheAge: this.agesDropdown.find((a: TrancheAge) => mobileUser.trancheAge?.dbValue === a.dbValue)?.enumName,
      dateFermeture: mobileUser.dateFermeture ? new Date(mobileUser.dateFermeture) : null,
    });
  }

  protected createFromForm(): MobileUser {
    return {
      ...new MobileUser(),

      id: this.editForm.get(['id'])!.value,
      pseudo: this.editForm.get(['pseudo'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      trancheAge: this.editForm.get(['trancheAge'])!.value,
      dateFermeture: this.editForm.get(['dateFermeture'])!.value,
    };
  }
}
