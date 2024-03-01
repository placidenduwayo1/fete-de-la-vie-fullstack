import { Component,Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpResponse } from '@angular/common/http';
import { InfosPageWeb } from 'src/app/model/webfdlv/fdlv-infos-page-web.model';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { InfosPageWebService } from 'src/app/services/webfdlv/fdlv-infos-page-web.service';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { Observable, finalize } from 'rxjs';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { EditorModule } from 'primeng/editor';
import { SharedModule } from 'primeng/api';
import {  CalendarModule } from 'primeng/calendar';
import { AngularEditorConfig, AngularEditorModule } from '@kolkov/angular-editor';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
@Component({
  selector: 'app-fdlv-info-page-web-create',
  standalone: true,
  imports: [CommonModule, SharedModule, CardModule, ButtonModule, ReactiveFormsModule, FormsModule,
    CalendarModule, AngularEditorModule,
    
    EditorModule],
  templateUrl: './fdlv-info-page-web-create.component.html',
  styleUrls: ['./fdlv-info-page-web-create.component.scss']
})

  export class FdlvInfoPageWebCreateComponent implements OnInit {
    isSaving = false;
    isCreate = false;
    isShow = false;
    isApercu = 'Aperçu';
    pagesWeb = ['1 = NOS ACTIONS', '2 = Valeurs/Points forts', '3 = Explo-Proto','Accueil']
    rubrique = ['Dernier Evènement', 'Devise', 'FDLV en Mots']
    infoPageWeb!: InfosPageWeb;
    required = 'Champs Obligatoire !';
    rubriqueRequired = 'Champs Obligatoire si la page est Accueil !';
    ckeditorDescription = '';
    durationInSeconds = 5;
    dateDebutInit!: Date;
    configCkEditor = {
      toolbarGroups: [
        { name: 'basicstyles', groups: ['basicstyles', 'cleanup'] },
        { name: 'paragraph', groups: ['align'] },
      ],
      removeButtons: 'Subscript,Superscript,Anchor,Source,Table',
      height: '7em',
    };
    editForm = this.fb.group({
      id: [],
      pageWeb: ["", [Validators.required]],
      rubriquePageWeb: [""],
      ordreAffichage: [null, [Validators.required]],
      titre: ["", [Validators.required]],
      texte: ["", [Validators.required]],
      commentaire: [null],
      publie: false,
      urlMedia: [null],
      dateDebut: [this.dateDebutInit, ],
      dateFin: null,
    });
    fileToUpload: File | null = null;
    displayBasic = false;
    url: string | ArrayBuffer | null = '';
  
    @Input() data: any;
    @Input() imgSide: any;

    config: AngularEditorConfig = {
      editable: true,
      spellcheck: true,
      height: '10rem',
      minHeight: '5rem',
      placeholder: 'Enter text in this rich text editor....',
      defaultParagraphSeparator: 'p',
      defaultFontName: 'Lato, sans-serif',
      toolbarHiddenButtons: [
        [
          'strikeThrough',
          'subscript',
          'superscript',
          'indent',
          'outdent',
          'heading',
          'fontName'
        ],
        [
          'fontSize',
          'textColor',
          'backgroundColor',
          'customClasses',
          'link',
          'unlink',
          'insertHorizontalRule',
          'removeFormat',
        ]
      ]
    };
  
  
    constructor(
      private infosPageWebService: InfosPageWebService,
      private fb: FormBuilder,
      private alertService: AlertService
    ) {}
  
    ngOnInit(): void {
        if(window.history.state.type== 'edit'){ 
          const id = Number(window.history.state.data);
        this.infosPageWebService.find(id).subscribe(response =>{
          let infosPageWeb = response.body;
            if (infosPageWeb.urlMedia) {
              this.url = infosPageWeb.urlMedia;
            }
            this.updateForm(infosPageWeb);
          })
      }
      this.editForm.get(['pageWeb'])!.valueChanges.subscribe((value)=>{
        const rubriqueControl = this.editForm.get(['rubriquePageWeb']);
        console.log(rubriqueControl.value)
        if(value=="Accueil"){
          rubriqueControl.setValidators([this.nonNullOuEgalAChoisirValidator]);
        }
        else{
          rubriqueControl.clearValidators();
        }
        rubriqueControl.updateValueAndValidity();
      })
    }
  
    ngOnChange(): void {
      this.infoPageWeb = {
        id: this.editForm.get(['id'])!.value,
        pageWeb: this.editForm.get(['pageWeb'])!.value,
        rubriquePageWeb: this.editForm.get(['rubriquePageWeb']).value,
        ordreAffichage: this.editForm.get(['ordreAffichage'])!.value,
        titre: this.editForm.get(['titre'])!.value,
        texte: this.editForm.get(['texte'])!.value,
        commentaire: this.editForm.get(['commentaire'])!.value,
        publie: this.editForm.get(['publie'])!.value,
        urlMedia: this.editForm.get(['urlMedia'])!.value,
        dateDebut: this.editForm.get(['dateDebut'])!.value,
        dateFin: this.editForm.get(['dateFin'])!.value,
      };
    }
  
    previousState(): void {
      window.location.href = '/infos-page-web-list';
    }
  
    eraseModification(): void {
      window.location.reload();
    }
  
    
  
    save(): void {
      this.isSaving = true;
      const infoPageWeb = this.createFromForm();
  
      if (infoPageWeb.id) {
        if (this.fileToUpload) {
          this.infosPageWebService.putImage(this.fileToUpload, infoPageWeb.titre).subscribe(result => {
            infoPageWeb.urlMedia = result.body!.value ?? '';
            this.subscribeToSaveResponse(this.infosPageWebService.updateInfo(infoPageWeb));
          });
        } else {
          this.subscribeToSaveResponse(this.infosPageWebService.updateInfo(infoPageWeb));
        }
      } else {
        if (this.fileToUpload) {
          this.infosPageWebService.putImage(this.fileToUpload, infoPageWeb.titre).subscribe(result => {
            infoPageWeb.urlMedia = result.body!.value ?? '';
            this.subscribeToSaveResponse(this.infosPageWebService.addInfos(infoPageWeb));
          });
        } else {
          this.subscribeToSaveResponse(this.infosPageWebService.addInfos(infoPageWeb));
        }
        this.isCreate = true;
      }
    }
  
    handleFileInput(input: EventTarget): void {
      this.fileToUpload = (<HTMLInputElement>input).files![0];
  
      // Preview image display
      const reader = new FileReader();
      reader.readAsDataURL(this.fileToUpload);
      reader.onload = event => {
        this.url = event.target!.result;
      };
    }
  
    subscribeToSaveResponse(result: Observable<HttpResponse<InfosPageWeb>>): void {
      result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
        next: () => this.onSaveSuccess(),
        error: () => this.onSaveError()
      }      
      );
    }
  
    onSaveSuccess(): void {
      if (this.isCreate) {
        this.isCreate = false;
      }
      this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Information", detail:"La sauvegarde est effectuée."})
      }

  protected onSaveError(): void {
    // Api for inheritance.
    this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Information", detail:"Echec lors de la modification."})
  }

    onSaveFinalize(): void {
      this.isSaving = false;
    }
  
    updateForm(info: any): void {
      this.editForm.patchValue({
        id: info.id,
        pageWeb: info.pageWeb,
        rubriquePageWeb: info.rubriquePageWeb,
        ordreAffichage: info.ordreAffichage,
        titre: info.titre,
        texte: info.texte,
        commentaire: info.commentaire,
        publie: info.publie,
        urlMedia: info.urlMedia,
        dateDebut: info.dateDebut ? new Date(info.dateDebut) : null,
        dateFin: info.dateFin ? new Date(info.dateFin) : null,
      });
    }
  
    createFromForm(): InfosPageWeb {
      return {
        ...new InfosPageWeb(),
        id: this.editForm.get(['id'])!.value,
        pageWeb: this.editForm.get(['pageWeb'])!.value,
        rubriquePageWeb:( this.editForm.get(['rubriquePageWeb'])!.value == "Choisir")?null : this.editForm.get(['rubriquePageWeb'])!.value,
        ordreAffichage: this.editForm.get(['ordreAffichage'])!.value,
        titre: this.editForm.get(['titre'])!.value,
        texte: this.editForm.get(['texte'])!.value,
        commentaire: this.editForm.get(['commentaire'])!.value,
        publie: this.editForm.get(['publie'])!.value,
        dateDebut: this.editForm.get(['dateDebut'])!.value,
        dateFin: this.editForm.get(['dateFin'])!.value,
        urlMedia: this.editForm.get(['urlMedia'])!.value
        
      };
    }
  
    onShow(): void {
      this.isShow ? (this.isApercu = 'Aperçu') : (this.isApercu = 'Cacher');
      this.isShow = !this.isShow;
    }
  
    getStyle(): string {
      let style = '';
      if (this.imgSide === 'left') {
        style = 'row';
      } else if (this.imgSide === 'right') {
        style = 'row-reverse';
      }
      return style;
    }
  
    get id(): number {
      return this.editForm.get(['id'])!.value as number;
    }

    nonNullOuEgalAChoisirValidator(control){
      if(control.value == "Choisir" || control.value == ''){
        return {nonNullOuEgalChoisir : true}
      }
      return null;
    }
  }
  
