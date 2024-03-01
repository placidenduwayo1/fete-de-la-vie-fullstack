import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ModalTypeEnum } from 'src/app/enums/modal-type.enum';
import { LogoTeaser } from 'src/app/model/logo-teaser.model';
import { Observable, finalize, first } from 'rxjs';
import { EntityResponseType, LogoTeaserService } from 'src/app/services/logo-teaser.service';

import { TableModule } from 'primeng/table'
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms'
import { InputTextModule } from 'primeng/inputtext'
import { ActivatedRoute, Router } from '@angular/router'
import { CardModule } from 'primeng/card'
import { ButtonModule } from 'primeng/button'
import { DialogModule } from 'primeng/dialog'


import { AlertService } from 'src/app/shared/alert/alert.service';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { FdlvUsersService } from 'src/app/services/fdlv-users.service';
import { FdlvUsers } from 'src/app/model/fdlv-user.model';
import { FileSelectEvent, FileUpload, FileUploadModule } from 'primeng/fileupload';
import { MediasService } from 'src/app/services/forum/medias.service';
import { MediaUploadModel } from 'src/app/model/forum/media-upload.model';
import { DisplayMediaComponent } from 'src/app/shared/display-media/display-media.component';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { AccountService } from 'src/app/pages/account/account.service';
import { FdlvUserService } from 'src/app/services/webfdlv/fdlv-users.service';

@Component({
  selector: 'app-logo-teaser-modal',
  standalone: true,
  imports: [CommonModule,
    TableModule,
    FormsModule,
    InputTextModule,
    CardModule,
    ButtonModule,
    DialogModule,
    FileUploadModule,
  DisplayMediaComponent, ToastModule, ReactiveFormsModule],
  providers:[MessageService],
  templateUrl: './logo-teaser-modal.component.html',
  styleUrls: ['./logo-teaser-modal.component.scss']
})
export class LogoTeaserModalComponent {
  modalType: ModalTypeEnum = ModalTypeEnum.CREATE;
  data: LogoTeaser = new LogoTeaser();

  title: string = "Créer";

  displayModal: boolean = false;
  fdlvUsers: FdlvUsers[];
  isLoading: boolean;

  fileToUpload: File;
  urlOnUserComputer: string;

  @ViewChild('fileUpload') fileUpload!: FileUpload;

  constructor(private mediaService :MediasService,private logoTeaserService: LogoTeaserService, private alertService: AlertService, 
    private route: Router, private fdlvUserService: FdlvUsersService, private messageService: MessageService) { }

  ngOnInit() {
    this.fdlvUserService.findAll().subscribe({
      next: (res: HttpResponse<FdlvUsers[]>) => {
        this.isLoading = false;
        this.fdlvUsers = res.body ?? [];
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }

  save() {
    if (this.fileToUpload) {

      const mediaData = new MediaUploadModel(this.fileToUpload, this.data.label, `fdlv/medias_organisateur/${this.data.typeMedia.toLowerCase()}s/`);
      //this.alertService.addMessage({ severity: AlertSeverity.INFO, summary: "Envoi du fichier.", detail: "En cours" });
      this.messageService.add({ severity: AlertSeverity.INFO, summary: "Envoi du fichier.", detail: "En cours" });
      //Upload du fichier
      this.mediaService.uploadMedia(mediaData).subscribe(
        {
          next: response => {
            this.data.url = response.mediaUrl;
            this.saveReferentiel()
            this.messageService.add({ severity: AlertSeverity.INFO, summary: "Envoi du fichier.", detail: "Envoi terminé" });
          },

          //Erreur upload
          error: (error: HttpErrorResponse) => {
            console.log(error)
            this.reloadPage()
            this.messageService.add({ severity: AlertSeverity.ERROR, summary: "Envoi du fichier.", detail: "Une erreur est survenue ! Le référentiel n'a pas pu être sauvegardé." });
          }

        });
    }
    else this.saveReferentiel();
  }

  saveReferentiel() {
    //Sauvegarde après upload
    var observable: Observable<EntityResponseType>;
    var titreAlerte: string;
    switch (this.modalType) {
      case ModalTypeEnum.CREATE: {
        observable = this.logoTeaserService.create(this.data);
        titreAlerte = "Création";
        break;
      }
      default: {
        observable = this.logoTeaserService.update(this.data);
        titreAlerte = "Modification";
        break;
      }
    }

    observable.pipe(first()).subscribe({
      next: response => {
        console.log(response)
        this.messageService.add({ severity: AlertSeverity.SUCCESS, summary: titreAlerte, detail: "Le référentiel a été sauvegardé." });
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
        this.reloadPage()
        this.messageService.add({ severity: AlertSeverity.ERROR, summary: titreAlerte, detail: "Une erreur est survenue ! Le référentiel n'a pas pu être sauvegardé." });
      }
    });
  }

  reloadPage() {
    this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.route.navigate(['logo-teaser-list'])
    })
  }

  open(modaltype: ModalTypeEnum, data?: LogoTeaser) {
    this.urlOnUserComputer= null;
    this.modalType = modaltype;

    switch (this.modalType) {
      case ModalTypeEnum.CREATE: {
        this.title = "Créer";
        this.data = new LogoTeaser()
        break;
      }
      case ModalTypeEnum.EDIT: {
        this.title = "Modifier";
        this.data = data;
        break;
      }
      default: {
        this.title = "Consultation";
        this.data = data;
        break;
      }
    }
    this.displayModal = true;
  }

  close() {
    this.displayModal = false;
    this.fileToUpload = null;
    this.urlOnUserComputer = null;
    this.fileUpload.clear();
  }

  
  handleFileInput(input: FileSelectEvent): void {
    console.log('handleFileInput');
    this.fileToUpload = input.currentFiles[0];

    // Preview image display
    const reader = new FileReader();
     reader.readAsDataURL(this.fileToUpload);
     reader.onload = event => {
       this.urlOnUserComputer = event.target!.result.toString();
     };
  }

  handleFileInputA(input: EventTarget): void {
    this.fileToUpload = (<HTMLInputElement>input).files![0];

    // Preview image display
    const reader = new FileReader();
    reader.readAsDataURL(this.fileToUpload);
    reader.onload = event => {
      this.urlOnUserComputer = event.target!.result.toString();
    };
  }


}
