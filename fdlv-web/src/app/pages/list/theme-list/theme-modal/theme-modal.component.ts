import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ITheme, Theme } from 'src/app/model/theme.model';
import { ModalTypeEnum } from 'src/app/enums/modal-type.enum';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, finalize, first } from 'rxjs';
import { EntityResponseType, ThemeService } from 'src/app/services/webfdlv/theme.service';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';


import { TableModule } from 'primeng/table'
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms'
import { InputTextModule } from 'primeng/inputtext'
import { CardModule } from 'primeng/card'
import { ButtonModule } from 'primeng/button'
import { DialogModule } from 'primeng/dialog'
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';


@Component({
  selector: 'app-theme-modal',
  standalone: true,
  imports: [CommonModule,
    TableModule,
    FormsModule, ReactiveFormsModule,
    InputTextModule,
    CardModule,
    ButtonModule,
    DialogModule, ToastModule],
    providers: [MessageService ],
  templateUrl: './theme-modal.component.html',
  styleUrls: ['./theme-modal.component.scss']
})
export class ThemeModalComponent {

isSaving = false;
isCreate = false;
durationInSeconds = 5;
editForm = this.fb.group({
  id: [],
  label: ["", [Validators.required]],
});

constructor(
  protected themeService: ThemeService,
  protected activatedRoute: ActivatedRoute,
  protected fb: FormBuilder,
  private alertService: AlertService,
  public router: Router
) {}

ngOnInit(): void {
  if(window.history.state.type== 'edit'){ 
    const theme = window.history.state.data;
    this.updateForm(theme);
  }
}

previousState(): void {
  window.location.href = '/theme-list';
}



save(): void {
  this.isSaving = true;
  const theme = this.createFromForm();
  if (theme.id) {
    this.subscribeToSaveResponse(this.themeService.update(theme));
  } else {
    this.subscribeToSaveResponse(this.themeService.create(theme));
    this.isCreate = true;
  }
}

onCancelCreation(): void {
  window.location.href = '/theme-list';
}

eraseModification(): void {
  window.location.reload();
}

get id(): number {
  return this.editForm.get(['id'])!.value as number;
}

protected subscribeToSaveResponse(result: Observable<HttpResponse<ITheme>>): void {
  result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
    () => this.onSaveSuccess(),
    () => this.onSaveError()
  );
}

protected onSaveSuccess(): void {
  if (this.isCreate) {
    this.isCreate = false;
  }
  this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Thème", detail:"La sauvegarde est effectuée."})
  }

  protected onSaveError(): void {
    this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Thème", detail:"Echec de la sauvegarde."})

  }

protected onSaveFinalize(): void {
  this.isSaving = false;
}

protected updateForm(theme: ITheme): void {
  this.editForm.patchValue({
    id: theme.id,
    label: theme.label,
  });
}

protected createFromForm(): ITheme {
  return {
    ...new Theme(),
    id: this.editForm.get(['id'])!.value,
    label: this.editForm.get(['label'])!.value,
  };
}
}

