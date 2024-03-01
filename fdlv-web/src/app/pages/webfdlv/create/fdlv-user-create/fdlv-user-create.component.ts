import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Profil, Profils } from 'src/app/model/profil.model';
import { Account } from 'src/app/core/auth/account.model';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { FdlvUserService } from 'src/app/services/webfdlv/fdlv-users.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { Observable, finalize, first, of } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { FdlvUsers } from 'src/app/model/fdlv-user.model';
import { CustomValidators } from 'src/app/pages/shared/custom-validators';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { AccountService } from 'src/app/pages/account/account.service';
import { CalendarModule } from 'primeng/calendar';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';

@Component({
  selector: 'app-fdlv-user-create',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule, FormsModule, ReactiveFormsModule, CalendarModule],
  templateUrl: './fdlv-user-create.component.html',
  styleUrls: ['./fdlv-user-create.component.scss']
})
export class FdlvUserCreateComponent implements OnInit {
  profils: Profil[] = Profils;
  account!: Account;
  isSaving = false;
  isCreate = false;
  durationInSeconds = 5;
  required = 'Champs Obligatoire !';
  myDate = new Date();
  identifiantAll: string[] = [];
  ident = false;
  identiantError!: string;
  login!: string;
  dateDebutInit!: Date;
  dateExpInit!: Date;
  editForm = this.fb.group(
    {
      id: [],
      login: [null, Validators.required],
      mdpHash: [
        null,
        Validators.compose([
          Validators.required,
          // Au moins un nombre
          CustomValidators.patternValidator(/\d/, { hasNumber: true }),
          // Au moins une majuscule
          CustomValidators.patternValidator(/[A-Z]/, { hasCapitalCase: true }),
          // Au moins une minuscule
          CustomValidators.patternValidator(/[a-z]/, { hasSmallCase: true }),
          // Au moins 1 caractère spécial
          CustomValidators.patternValidator(/[#?!@$%^&*-+=<>€]/, { hasSpecialCharacters: true }),
          // Au moins 8 caractères
          Validators.minLength(8),
        ]),
      ],
      profil: [null, [Validators.required]],
      prenom: [null, [Validators.required]],
      nom: [null, [Validators.required]],
      structure: [null, [Validators.required]],
      service: [null],
      email: [null, Validators.compose([Validators.email, Validators.required])],
      numTel: [null, [Validators.required]],
      actif: [null],
      dateDebut: [this.dateDebutInit, [Validators.required]],
      dateFin: [this.dateExpInit],
      permanent: [null],
    },
    { validator: [CustomValidators.fromToDate('dateDebut', 'dateFin')] }
  );
  constructor(
    protected fdlvUserService: FdlvUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private accountService: AccountService,
    public router: Router,
    private alertService: AlertService
  ) { }

  ngOnInit(): void {

    if (window.history.state.type == 'edit') {
      const id = window.history.state.data
      this.fdlvUserService.find(id).subscribe(res=>{
        const fdlvUser = res.body;
        this.updateForm(fdlvUser);
      })
    }
    this.accountService.getAccount().pipe(first()).subscribe({
      next: (response: Account) => this.account = response
    });

    this.fdlvUserService.findAll().subscribe(data => {
      data.body?.forEach(identifiant => {
        this.identifiantAll.push(identifiant.login!);
      });
    });
  }

  previousState(): void {
    window.location.href = '/fdlv-user-web-list';
  }


  save(): void {
    if (!this.ident) {
      this.isSaving = true;
      const fdlvUser = this.createFromForm();

      // Si compte permanent alors + 6 mois par rapport date deb
      if (this.editForm.get(['permanent'])!.value) {
        fdlvUser.dateFin = fdlvUser.dateDebut?.add(6, 'month');
      }

      // Si compte pas permanent mais pas date fin selectionner alors + 14 jours
      if (fdlvUser.dateFin === undefined) {
        fdlvUser.dateFin = fdlvUser.dateDebut?.add(14, 'day');
      }

      if (fdlvUser.id) {
        this.subscribeToSaveResponse(this.fdlvUserService.update(fdlvUser));
      } else {
        console.warn(fdlvUser);
        this.subscribeToSaveResponse(this.fdlvUserService.create(fdlvUser));
        this.isCreate = true;
      }
    } else {
      this.identiantError = 'Identifiant déja utilisé, faites un autre choix';
    }
  }

  onCancelCreation(): void {
    window.location.href = '/fdlv-user-web-list';
  }

  eraseModification(): void {
    window.location.reload();
  }

  uniqueIdentifiant(event: any): void {
    this.login = event.target.value.toString();
    if (this.identifiantAll.indexOf(this.login) !== -1) {
      this.ident = true;
    } else {
      this.ident = false;
    }
    this.login = '';
  }

  get id(): number {
    return this.editForm.get(['id'])!.value as number;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<FdlvUsers>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    if (this.isCreate) {
      this.isCreate = false;
    }
    this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Utilisateur", detail:"La sauvegarde est effectuée."})
  }

  
  protected onSaveError(): void {
    // Api for inheritance.
    this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Utilisateur", detail:"Echec lors de la sauvegarde."})

  }
  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(fdlvUser: any): void {
    this.editForm.patchValue({
      id: fdlvUser.id,
      profil: this.profils.find((p: Profil) => fdlvUser.profil?.dbValue === p.dbValue)?.enumName,
      login: fdlvUser.login,
      mdpHash: fdlvUser.mdpHash,
      prenom: fdlvUser.prenom,
      nom: fdlvUser.nom,
      structure: fdlvUser.structure,
      service: fdlvUser.service,
      email: fdlvUser.email,
      numTel: fdlvUser.numTel,
      actif: fdlvUser.actif,
      dateDebut: fdlvUser.dateDebut ? new Date(fdlvUser.dateDebut) : null,
      dateFin: fdlvUser.dateFin ? new Date(fdlvUser.dateFin) : null,
      permanent: fdlvUser.permanent,
      resetKey: fdlvUser.resetKey,
      creePar: fdlvUser.creePar,
      dateCreation: fdlvUser.dateCreation,
      dateReset: fdlvUser.dateReset,
      modifiePar: fdlvUser.modifiePar,
      dateModif: fdlvUser.dateModif,
    });
  }

  protected createFromForm(): FdlvUsers {
    return {
      ...new FdlvUsers(),

      id: this.editForm.get(['id'])!.value,
      profil: this.editForm.get(['profil'])!.value,
      login: this.editForm.get(['login'])!.value,
      mdpHash: this.editForm.get(['mdpHash'])!.value,
      prenom: this.editForm.get(['prenom'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      structure: this.editForm.get(['structure'])!.value,
      service: this.editForm.get(['service'])!.value || null,
      email: this.editForm.get(['email'])!.value,
      numTel: this.editForm.get(['numTel'])!.value,
      actif: this.editForm.get(['actif'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value,
      dateFin: this.editForm.get(['dateFin'])!.value,
      permanent: this.editForm.get(['permanent'])!.value,
      resetKey: null!,
      creePar: this.account.login, // Récupération du login qui a créé le compte
      dateCreation: null!,
      dateReset: null!,
      modifiePar: null!,
      dateModif: null!,
      fromFDLV: false,
    };
  }
}
