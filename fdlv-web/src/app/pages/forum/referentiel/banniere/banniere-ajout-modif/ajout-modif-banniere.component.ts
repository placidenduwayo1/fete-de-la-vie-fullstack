import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
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
import { DialogModule } from 'primeng/dialog';
import { CheckboxModule } from 'primeng/checkbox';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { MessageModule } from 'primeng/message';
import { ToastModule } from 'primeng/toast';
import { InputSwitchModule } from 'primeng/inputswitch';
import { Calendar, CalendarModule } from 'primeng/calendar';
import { MediasService } from 'src/app/services/forum/medias.service';
import { MediaUploadModel } from 'src/app/model/forum/media-upload.model';
import { BanniereService } from 'src/app/services/forum/banniere.service';
import { ForumThemeService } from 'src/app/services/forum/forum-theme.service';
import { Banniere } from 'src/app/model/forum/banniere.model';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-ajout-modif-banniere',
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
    DropdownModule,
    MessageModule
  ],
  providers: [
    MessageService
  ],
  templateUrl: './ajout-modif-banniere.component.html',
  styleUrls: ['./ajout-modif-banniere.component.scss']
})
export class AjoutModifBanniereComponent implements OnInit {
  @ViewChild('calendar') calendar: Calendar;
  @ViewChild('calendarWrapper') calendarWrapper: ElementRef;
  style: { [klass: string]: any } = {
    height: '30px',
    width: '158px',
  };
  urlModif = 'forum/referentiel/banniere';
  urlList = 'forum/referentiel/banniere';
  data: any = [];
  banniereForm: FormGroup;
  datauser: any = {};
  modifMode: boolean = false;
  themeForumOptions: any[] = [];
  displayPhoto: string = null;
  constructor(
    private banniereService: BanniereService,
    private forumThemeService: ForumThemeService,
    private mediaService: MediasService,
    private route: Router,
    private fg: FormBuilder,
    private messageService: MessageService
  ) {
    this.banniereForm = this.fg.group({
      id: null,
      code:  [null, Validators.required],
      libelle: [null, Validators.required],
      forumThemeId: null,
      fdlv: null,
      url: null,
      commentaire: null
    });
  }

  ngOnInit() {
    this.forumThemeService.getAllForumTheme()
      .subscribe((response: any = []) => {
        this.themeForumOptions = response.map((themeForum: any) => ({
          label: `${themeForum.code}` + `: ${themeForum.libelle}`,
          value: themeForum.id
        }));
      });
      if (window.history.state.type == 'modification') {
        this.modifMode = true;
        this.datauser = window.history.state.data;
        this.initialisationFb();
      }
  }

  closeForm() {
    this.route.navigate([this.urlList]);
  }

  eraseModification(): void {
    window.location.reload();
  }

  saveOrEditBanniere(banniere: Banniere): void{
    banniere = this.validateMandatoryFields(banniere);
    if(banniere == null) return;
    const fileUpload = (<HTMLInputElement>document.getElementById("fileBanniere")).files![0];
    if(fileUpload != null) {
      const code = this.banniereForm.get("code").value;
      const mediaData = new MediaUploadModel(fileUpload, code, "banniere");
      this.mediaService.uploadMedia(mediaData).subscribe((response) => {
        banniere.url = response.mediaUrl;
        this.processModification(banniere);
      });
    } else {
      this.processModification(banniere);
    }
  }

  processModification(banniere: any){
    if(this.modifMode){
      this.banniereService.updateBanniere(banniere).subscribe({
        next: (response: HttpResponse<Banniere>) => {
          console.log(response);
          this.route.navigate([this.urlModif], { state: { data: banniere , type:'modification'} });
        },
        error: (error: any) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Erreur lors de la modification.',
            detail: 'Certaines données requises sont manquantes ou invalides: ' + error.error.title
          });
        },
      });
    } else {
      this.banniereService.createBanniere(banniere).subscribe({
        next: (response) => {
          this.route.navigate([this.urlModif], { state: { data: banniere , type:'modification'} });
        },
        error: (error: HttpErrorResponse) => {
          console.log('Error title:', error.error.title);
          this.messageService.add({
            severity: 'error',
            summary: 'Erreur lors de la création.',
            detail: 'Certaines données requises sont manquantes ou invalides: ' + error.error.title
          });
        },
      });
    }
  }

  validateMandatoryFields(banniere : Banniere) : Banniere{
    if(banniere.code === null || banniere.code.length > 20){
      this.banniereForm.get('code')?.setErrors({
        codeBanniereError: true
      });
      return null;
    }
    if(banniere.libelle === null || banniere.libelle.length == 0){
      this.banniereForm.get('libelle')?.setErrors({
        libelleBanniereError: true
      });
      return null;
    }
    if(banniere.fdlv == null || banniere.fdlv == ''){
      banniere.fdlv = 'FDLV';
    }
    const fileUpload = (<HTMLInputElement>document.getElementById("fileBanniere")).files![0];
    if(fileUpload == null && banniere.url == null){
      this.messageService.add({
        severity: 'error',
        summary: 'Erreur lors de la création.',
        detail: 'Merci de télécharger la photo du bannière.'
      });
      return null;
    }
    return banniere;
  }

  initialisationFb() {
    this.banniereForm = this.fg.group({
      id: this.datauser?.id,
      code: this.datauser?.code,
      libelle: this.datauser?.libelle,
      forumThemeId: this.datauser?.forumThemeId,
      url: this.datauser?.url,
      commentaire: this.datauser?.commentaire
    });
    this.displayPhoto = this.datauser?.url;
    console.log('Banniere:', this.banniereForm.value);
  }

  previewDocument(input: EventTarget): void {
    const preview = (<HTMLInputElement>input).files![0];
    const reader = new FileReader();
    reader.readAsDataURL(preview);
    reader.onload = event => {
      this.displayPhoto = event.target?.result as string;
    };
  }
}
