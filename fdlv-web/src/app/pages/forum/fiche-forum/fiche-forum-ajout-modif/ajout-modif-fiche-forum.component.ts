import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { DropdownModule } from 'primeng/dropdown';
import {FileUploadModule} from 'primeng/fileupload';
import { TagModule } from 'primeng/tag';
import { CommonModule } from '@angular/common';
import { PanelModule } from 'primeng/panel';
import { TableModule } from 'primeng/table';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { NavbarComponent } from 'src/app/ui/navbar/navbar.component';
import { Router, RouterOutlet } from '@angular/router';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { ForumService } from '../../../../services/forum/forum.service';
import { ForumTypeService } from '../../../../services/forum/forum-type.service';
import { DialogModule } from 'primeng/dialog';
import { CheckboxModule } from 'primeng/checkbox';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { InputSwitchModule } from 'primeng/inputswitch';
import { Calendar, CalendarModule } from 'primeng/calendar';
import { Forum } from 'src/app/model/forum/forum.model';
import { MediasService } from 'src/app/services/forum/medias.service';
import { MediaUploadModel } from 'src/app/model/forum/media-upload.model';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';

@Component({
  selector: 'app-ajout-modif-fiche-forum',
  standalone: true,
  imports: [
    InputSwitchModule,
    CommonModule,
    ToastModule,
    PanelModule,
    TableModule,
    FormsModule,
    InputTextModule,
    FileUploadModule,
    NavbarComponent,
    RouterOutlet,
    CardModule,
    TagModule,
    ButtonModule,
    RippleModule,
    DialogModule,
    CheckboxModule,
    MessageModule,
    CalendarModule,
    ReactiveFormsModule,
    ToastModule,
    DropdownModule
  ],
  templateUrl: './ajout-modif-fiche-forum.component.html',
  styleUrls: ['./ajout-modif-fiche-forum.component.scss']
})
export class AjoutModifFicheForumComponent implements OnInit {
  @ViewChild('calendar') calendar: Calendar;
  @ViewChild('calendarWrapper') calendarWrapper: ElementRef;
  style: { [klass: string]: any } = {
    height: '30px',
    width: '158px',
  };
  datesFormatees: any = {};
  data: any = [];
  class: any;
  ForumForm: FormGroup;
  datauser: Forum = {};
  modifMode: boolean = false;
  typeForumOptions: any[] = [];
  fileConvention: string | null = null;
  filePlan: string | null = null;
  fileSalle: string | null = null;
  constructor(
    private ForumService: ForumService,
    private ForumTypeService: ForumTypeService,
    private mediaService: MediasService,
    private route: Router,
    private fg: FormBuilder,
    private alertService: AlertService
  ) {
    this.ForumForm = this.fg.group({
      id: null,
      numForum: [null, [Validators.required, Validators.pattern(/^[0-9]+$/)]],
      libelle: [null, Validators.required],
      reference: null,
      typeForum: null,
      setFormValid : false,
      isValid: '0',
      sloganForum: null,
      dateFinEvenement: null,
      dateFinReservation: null,
      dateFermeture: null,
      heureFermeture: null,
      dateDebutEvenement: [null, Validators.required],
      dateDebutReservation: null,
      dateOuverture: [null, Validators.required],
      heureOuverture: [null, Validators.required],
      adresseForum: null,
      complementAdresseForum: null,
      codePostal: [null, Validators.required],
      commune: null,
      lieuForum: null,
      conventionUrl: null,
      planForumUrl: null,
      planSalle: null,
      descriptionConvention: null,
      commentaire: null
    });
  }

  ngOnInit() {
    this.ForumTypeService.findAll()
      .subscribe((response: any = []) => {
        this.typeForumOptions = response.map((typeForum: any) => ({
          label: `${typeForum.type}`, // Afficher libellé et ID
          value: typeForum.code, // Utilisez l'objet complet comme valeur
        }));
      });
      if (window.history.state.type == 'modification') {
        this.modifMode = true;
        this.datauser = window.history.state.data;
        this.initialisationFb();
      }
  }

  onMediaUpload(event: any) {
    const file: File = event.target.files[0];
    const mediaData = new MediaUploadModel(file, 'MyMediaName', 'MyMediaPath');

    this.mediaService.uploadMedia(mediaData).subscribe((response) => {
      console.log('hhhhhhhhh', response.imageUrl);
      console.log(
        'Media uploaded successfully. Name: ' +
          response.imageUrl +
          ', URL: ' +
          response.imageUrl
      );
    });
  }

  closeForm() {
    this.route.navigate(['forum/fiche-forum']);
  }

  eraseModification(): void {
    window.location.reload();
  }

  saveForm(forum: any) {
    //Champs obligatoire à valider:
    if(!this.validateMandatoryFields()) return;
    
    const now = new Date();
    if(forum.dateDebutEvenement === null || forum.dateDebutEvenement < now){
      this.ForumForm.get('dateDebutEvenement')?.setErrors({
        dateDebutEvenementError: true
      });
      return;
    }
    forum = this.validateHourForum(forum);
    forum = this.updateUrlDocument(forum);
    this.ForumService.create(forum).subscribe({
      next: (response) => {
        this.route.navigate(['ficheforum-modif'], { state: { data: forum , type:'modification'} });
        this.alertService.addMessage({severity: AlertSeverity.SUCCESS,summary:"Forum", detail:"Nouveau forum crée."});
      },
      error: (error: HttpErrorResponse) => {
        console.log('Error title:', error.error.title);
        this.alertService.addMessage({severity: AlertSeverity.ERROR,summary:"Forum", detail:"Erreur lors de la création du forum."});
      },
    });
  }

  editForm(forum: any) {
    //Champs obligatoire à valider:
    if(!this.validateMandatoryFields()) return;

    const debutE = this.ForumForm.get('dateDebutEvenement');
    if (debutE.value == null){
      debutE.setErrors({ ['dateDebutEvenementError']: true });
      return;
    }
    
    if(forum.setFormValid){
      //Valid all fields:
      const debutR = this.ForumForm.get('dateDebutReservation');
      const finR = this.ForumForm.get('dateFinReservation');
      if (debutR.value == null){
        debutR.setErrors({ ['dateDebutReservationError']: true });
        return;
      }
      if (finR.value == null || finR.value < debutR.value){
        finR.setErrors({ ['dateFinReservationError']: true });
        return;
      }
      const finE = this.ForumForm.get('dateFinEvenement');
      if (finE.value == null || finE.value < debutE.value){
        finE.setErrors({ ['dateFinEvenementError']: true });
        return;
      }
      const ouv = this.ForumForm.get('dateOuverture');
      const fer = this.ForumForm.get('dateFermeture');
      if (ouv.value == null){
        ouv.setErrors({ ['dateOuvertureError']: true });
        return;
      }
      if (fer.value == null || fer.value < ouv.value){
        fer.setErrors({ ['dateFermetureError']: true });
        return;
      }
      const hourF = this.ForumForm.get('heureFermeture');
      const hourO = this.ForumForm.get('heureFermeture');
      if(hourO.value == null || hourF.value == null){
        hourF.setErrors({ ['heureFermetureError']: true });
        return;
      }
      forum.isValid = '1';
    } else {
      forum.isValid = '0';
    }
    forum = this.validateHourForum(forum);
    forum = this.updateUrlDocument(forum);
    this.ForumService.update(this.datauser.id, forum).subscribe({
      next: (response: HttpResponse<Forum>) => {
        console.log(response);
        this.route.navigate(['ficheforum-modif'], { state: { data: forum , type:'modification'} });
        this.alertService.addMessage({severity: AlertSeverity.SUCCESS,summary:"Forum", detail:"La sauvegarde est effectuée."});
      },
      error: (error: any) => {
        this.alertService.addMessage({severity: AlertSeverity.ERROR,summary:"Forum", detail:"Erreur: " + error.error.title});
      },
    });
  }

  validateMandatoryFields() : boolean{
    if(!this.validateForumNumber())   return false;
    if(!this.validateForumLibelle())  return false;
    if(!this.validateCodePostale())   return false;
    return true;
  }

  reloadPage() {
    this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.route.navigate(['forum/fiche-forum']);
    });
  }

  initialisationFb() {
    this.ForumForm = this.fg.group({
      id: this.datauser?.id,
      numForum: this.datauser?.numForum,
      libelle: this.datauser?.libelle,
      reference: this.datauser?.reference,
      typeForum: this.datauser?.typeForum,
      setFormValid: this.datauser?.isValid === '1' ? true : false,
      isValid: this.datauser?.isValid,
      sloganForum: this.datauser?.sloganForum,
      dateFinEvenement: this.datauser?.dateFinEvenement
        ? new Date(this.datauser?.dateFinEvenement) : null,
      dateFinReservation: this.datauser?.dateFinReservation
        ? new Date(this.datauser?.dateFinReservation) : null,
      dateFermeture: this.datauser?.dateFermeture
        ? new Date(this.datauser?.dateFermeture) : null,
      heureFermeture: this.datauser?.heureFermeture
        ? this.formatDateWithTime(this.datauser?.heureFermeture) : null,
      dateDebutEvenement: this.datauser?.dateDebutEvenement
        ? new Date(this.datauser?.dateDebutEvenement) : null,
      dateDebutReservation: this.datauser?.dateDebutReservation
        ? new Date(this.datauser?.dateDebutReservation) : null,
      dateOuverture: this.datauser?.dateOuverture
        ? new Date(this.datauser?.dateOuverture) : null,
      heureOuverture: this.datauser?.heureOuverture
        ? this.formatDateWithTime(this.datauser?.heureOuverture) : null,
      adresseForum: this.datauser?.adresseForum,
      complementAdresseForum: this.datauser?.complementAdresseForum,
      codePostal: this.datauser?.codePostal,
      commune: this.datauser?.commune,
      lieuForum: this.datauser?.lieuForum,
      conventionUrl: this.datauser?.conventionUrl,
      planForumUrl: this.datauser?.planForumUrl,
      planSalle: this.datauser?.planSalle,
      descriptionConvention: this.datauser?.descriptionConvention,
      commentaire: this.datauser?.commentaire,
    });
    console.log('ForumForm:', this.ForumForm.value);
  }

  validateCodePostale() : boolean {
    const cp = this.ForumForm.get('codePostal')?.value;
    if (cp == null || cp.toString().length !== 5) {
      this.ForumForm.get('codePostal')?.setErrors({
        codePostalError: true
      });
      return false;
    }
    return true;
  }

  validateForumNumber(): boolean {
    const fnb = this.ForumForm.get('numForum')?.value;
    if(fnb === null || isNaN(fnb) || fnb.toString().length !== 5){
      this.ForumForm.get('numForum')?.setErrors({
        numForumError: true
      });
      return false;
    }
    return true;
  }

  validateForumLibelle(): boolean{
    const lib = this.ForumForm.get('libelle')?.value;
    if(lib === null || lib.length < 5){
      this.ForumForm.get('libelle')?.setErrors({
        libelleForumError: true
      });
      return false;
    }
    return true;
  }

  validateHourForum(forum: any): any {
    const hourF = this.ForumForm.get('heureFermeture');
    const hourO = this.ForumForm.get('heureOuverture');
    if(hourF.value != null){
      forum.heureFermeture = this.formatDateToLocalTime(hourF.value);
    } 
    if(hourO.value != null){
      forum.heureOuverture = this.formatDateToLocalTime(hourO.value);
    }
    return forum;
  }

  formatDateToLocalTime(date: Date): string {
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const seconds = date.getSeconds().toString().padStart(2, '0');
    const localTime = `${hours}:${minutes}:${seconds}`;
    return localTime;
  }
  
  formatDateWithTime(time: String): Date {
    const currentDate = new Date();
    const [hours, minutes, seconds] = time.split(':');
    currentDate.setHours(+hours, +minutes, +seconds);
    return currentDate;
  }

  previewDocument(input: EventTarget, typeDocument: string): void {
    const preview = (<HTMLInputElement>input).files![0];
    const numForum = this.ForumForm.get("numForum").value;
    const mediaData = new MediaUploadModel(preview, numForum + "-" + typeDocument, "forum");
    this.mediaService.uploadMedia(mediaData).subscribe((response) => {
      if(typeDocument == "conventionUrl"){
        this.fileConvention = response.mediaUrl;
      }
      if(typeDocument == "planForumUrl"){
        this.filePlan = response.mediaUrl;
      }
      if(typeDocument == "planSalle"){
        this.fileSalle = response.mediaUrl;
      }
    });
    // Preview image display
    const reader = new FileReader();
    reader.readAsDataURL(preview);
    reader.onload = event => {
      this.ForumForm.get(typeDocument).setValue(event.target?.result);
    };
  }

  updateUrlDocument(forum: any): any {
    if(this.filePlan != null){
      forum.planForumUrl = this.filePlan;
      this.filePlan = null;
    }
    if(this.fileConvention != null){
      forum.conventionUrl = this.fileConvention;
      this.fileConvention = null;
    }
    if(this.fileSalle != null){
      forum.planSalle = this.fileSalle;
      this.fileSalle = null;
    }
    return forum;
  }
}
