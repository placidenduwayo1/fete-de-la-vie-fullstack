import { Component, OnDestroy, OnInit, Pipe } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FdlvVideo, getTypeOfMedias } from 'src/app/model/webfdlv/fdlv-video.model';
import { Observable, Subject, finalize, first, takeUntil } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { FdlvUsers } from 'src/app/model/fdlv-user.model';
import { FdlvUserService } from 'src/app/services/webfdlv/fdlv-users.service';
import { FdlvVideoService } from 'src/app/services/webfdlv/fdlv-video.service';
import { ThemeService } from 'src/app/services/webfdlv/theme.service';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ITheme, Theme } from 'src/app/model/theme.model';
import { ActivatedRoute } from '@angular/router';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { PickListModule } from 'primeng/picklist';
import { DialogModule } from 'primeng/dialog';
import { TableModule } from 'primeng/table';
import { QuizzService } from 'src/app/services/quizz.service';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { MediaUploadModel } from 'src/app/model/forum/media-upload.model';
import { MediasService } from 'src/app/services/forum/medias.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-fdlv-video-create',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule, PickListModule,
     DialogModule, TableModule, FormsModule, ReactiveFormsModule, ProgressSpinnerModule],
  templateUrl: './fdlv-video-create.component.html',
  styleUrls: ['./fdlv-video-create.component.scss']
})
export class FdlvVideoCreateComponent implements OnInit, OnDestroy {
  isSaving = false;
  isLoading = true;
  fdlvUsers?: FdlvUsers[];
  isCreate = false;
  fdlvVideo = {
    ...new FdlvVideo(),
  };
  typeOfMedias: string[] = getTypeOfMedias();
  required = 'Champ Obligatoire !';
  errorExtension = "l'extension du média n'est pas valide";
  ckeditorDescription = '';
  durationInSeconds = 5;
  configCkEditor = {
    toolbarGroups: [
      { name: 'basicstyles', groups: ['basicstyles', 'cleanup'] },
      { name: 'paragraph', groups: ['align'] },
    ],
    removeButtons: 'Subscript,Superscript,Anchor,Source,Table',
    height: '7em',
  };
  
  numOrdreInit!: number;
  flvFusIdInit!: number;
  themeInit!: ITheme;
  editForm = this.fb.group({
    id: [],
    theme: [this.themeInit, [Validators.required]],
    numOrdre: [this.numOrdreInit, [Validators.required]],
    nomVideo: ["", [Validators.required]],
    urlVideo: ["", [Validators.required]],
    typeMedia: [""],
    urlImage: ["", [Validators.required]],
    description: ["", [Validators.required]],
    active: [false],
    flvFusId: [this.flvFusIdInit, [Validators.required]],
    flvMediaValide: [null],
  });
  themeList?: ITheme[];
  displayBasic = false;
  allQuizzList: any;
  items: any[] = [];
  selectedQuizzLinkedToID: any[] = [];
  allQuizzFromVideoList: any;
  idVideo!: number;
  videoQuizzListTable: any;
  urlImg: string | null = '';
  urlVideo: string | null = '';
  fileToUpload:  File | undefined;
  extensionFile: string;
  incorrect = false;

  // Attribut qui va permettre d'envoyer des données pour OnDestroy
  private $destroy = new Subject<void>();

  constructor(
    public quizzService: QuizzService,
    protected fdlvVideoService: FdlvVideoService,
    protected themeService: ThemeService,
    protected fdlvUserService: FdlvUserService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private alertService: AlertService,
    private mediaService: MediasService,
    private sanitizer: DomSanitizer
  ) {}

  async loadAll(): Promise<void> {
    this.isLoading = true;
    const promiseThemes = await this.themeService.querySync();
    this.fdlvUserService.findAll().subscribe(
      (res: HttpResponse<FdlvUsers[]>) => {
        this.isLoading = false;
        this.fdlvUsers = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
    this.themeList = promiseThemes.body;
    this.isLoading = false;
  }

  async ngOnInit(): Promise<void> {
    await this.loadAll();
    if(window.history.state.type== 'edit'){ 
      const fdlvVideo = window.history.state.data
      this.loadAllQuizzFromVideo(fdlvVideo);

      if (fdlvVideo.urlImage) {
        this.urlImg = fdlvVideo.urlImage;
      }if (fdlvVideo.urlVideo) {
        this.urlVideo = fdlvVideo.urlVideo;
      }
      this.updateForm(fdlvVideo);
    }
    this.loadAllQuizzData();
  }

  ngOnDestroy(): void {
    this.$destroy.next();
    this.$destroy.complete();
  }

  ngOnChange(): void {
    this.fdlvVideo = {
      id: this.editForm.get(['id'])!.value,
      theme: this.editForm.get(['theme'])!.value,
      numOrdre: this.editForm.get(['numOrdre'])!.value,
      nomVideo: this.editForm.get(['nomVideo'])!.value,
      urlVideo: this.editForm.get(['urlVideo'])!.value,
      typeMedia: this.editForm.get(['typeMedia'])!.value,
      urlImage: this.editForm.get(['urlImage'])!.value,
      description: this.editForm.get(['description'])!.value,
      active: this.editForm.get(['active'])!.value,
      flvFusId: this.editForm.get(['flvFusId'])!.value,
      flvMediaValide: this.editForm.get(['flvMediaValide'])!.value,
    };
  }

  previousState(): void {
    window.location.href = '/fdlv-video-list';
  }

  loadAllQuizzFromVideo(fdlvVideo: FdlvVideo): void {
    const quizzVideoList: any[] = [];
    const quizzVideoListOfTable: any[] = [];

    // Récupère la liste des quizz associée à la vidéo dans la variable quizzVideoList
    this.allQuizzFromVideoList = fdlvVideo.quizzs?.forEach((e: any) => {
      quizzVideoList.push({ id: e.id, label: e.label });
    });

    // Récupère seulement le label pour afficher dans le tableau
    for (let i = 0; i < quizzVideoList.length; i++) {
      quizzVideoListOfTable.push(quizzVideoList[i]['label']);
    }
    this.videoQuizzListTable = quizzVideoListOfTable; // videoQuizzListTable : variable qui contient les quizz liés à la vidéo (seulement label)
    this.selectedQuizzLinkedToID = quizzVideoList; // selectedQuizzLinkedToID : variable qui contient les quizz liés à la vidéo (id et label)

  }

  // Récupère la liste des quizz complète
  loadAllQuizzData(): void {
    // Récupère tout les quizz
    this.quizzService.findAll().pipe(first())
    .subscribe(
      quizzes => {
        const initialList: any[] = [];
        quizzes.forEach(e => {
          initialList.push({ id: e.id, label: e.label }); // initialList : contient tout les quizz (id et label)
  
      });

        // Supprime les doublons des quizz liés avec la vidéo
        this.items = initialList.filter(item => !this.selectedQuizzLinkedToID.find((i: any) => i.id === item.id)) ;

      });
  }

  showBasicDialog(): void {
    this.displayBasic = true;
  }

  // Met à jour le lien entre video et quizz
  dataUpdateVideoQuizz(): void {
    const quizzIDlist: number[] = [];
    this.selectedQuizzLinkedToID?.forEach((e: any) => {
      quizzIDlist.push(e.id);
    });
  }

  eraseModification(): void {
    window.location.reload();
  }


  save(): void {
    this.isSaving = true;
    const fdlvVideo = this.createFromForm();
    fdlvVideo.quizzs = this.selectedQuizzLinkedToID;
    fdlvVideo.urlImage = this.urlImg;
    fdlvVideo.urlVideo = this.urlVideo;
  
   if (fdlvVideo.id) {
      this.subscribeToSaveResponse(this.fdlvVideoService.update(fdlvVideo));
    } else {
     fdlvVideo.theme = this.getThemeByID( Number(this.editForm.get('theme')?.value)); 
      this.subscribeToSaveResponse(this.fdlvVideoService.create(fdlvVideo));
      this.isCreate = true;
    }
  }

  getFileName(fileToUpload){
    let fileName = fileToUpload.name;
    this.extensionFile =  fileName.split('.').pop();
    return fileName.replace("."+this.extensionFile, "");
  }
  verifyMediaExtension(): void {
    if (
      this.editForm.get(['typeMedia'])!.value != null &&
      this.editForm.get(['typeMedia'])!.value !== '' &&
      this.editForm.get(['urlVideo'])!.value != null &&
      this.editForm.get(['urlVideo'])!.value !== ''
    ) {
      const typemedia = this.editForm.get(['typeMedia'])!.value;
      const urlVideo = this.editForm.get(['urlVideo'])!.value;
      const lastIndex = urlVideo.lastIndexOf('.');
      const extension = urlVideo.substring(lastIndex, urlVideo.length);

      if (typemedia !== extension) {
        this.editForm.get(['urlVideo'])!.setErrors({ incorrect: true });
      } else {
        this.editForm.get(['urlVideo'])!.setErrors({});
        this.editForm.get(['urlVideo'])!.updateValueAndValidity();
      }
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<FdlvVideo>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    if (this.isCreate) {
      this.isCreate = false;
    }
    this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Média", detail:"La sauvegarde est effectuée."})
  }

  protected onSaveError(): void {
    // Api for inheritance.
    this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Média", detail:"Echec lors de la sauvegarde."})
  }


  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(fdlvVideo: FdlvVideo): void {
    this.editForm.patchValue({
      id: fdlvVideo.id,
      theme: fdlvVideo.theme,
      numOrdre: fdlvVideo.numOrdre,
      urlVideo: fdlvVideo.urlVideo,
      urlImage: fdlvVideo.urlImage,
      typeMedia: fdlvVideo.typeMedia,
      description: fdlvVideo.description,
      active: fdlvVideo.active,
      nomVideo: fdlvVideo.nomVideo,
      flvFusId: fdlvVideo.flvFusId,
      flvMediaValide: fdlvVideo.flvMediaValide,
    });

    // Envoie de l'id de la video vers idVideo
    if (fdlvVideo.id) {
      this.idVideo = fdlvVideo.id;
    }
  }

  handleFileInput(input: EventTarget, type: string): void {
    const fileToUpload = (<HTMLInputElement>input).files![0];
      // Preview image display
  if(fileToUpload){
  // verify authorized extension
    const fileName =  this.getFileName(fileToUpload);
    const path = this.getPathByTypeMedia(this.extensionFile);
    if(this.typeOfMedias.includes("."+this.extensionFile)){
      
    this.incorrect =false;
    const mediaData =  new MediaUploadModel(fileToUpload, fileName, 'fdlv/medias_organisateur/'+path+'/')
    this.mediaService.uploadMedia(mediaData).subscribe((response) => {
      if(type == "urlVideo"){
        this.urlVideo = response.mediaUrl;
        this.editForm.patchValue({
           typeMedia: this.getMediaType(this.urlVideo)
           });
      }
      if(type == "urlImage"){
        this.urlImg = response.mediaUrl;
      }
    });
 
    // Preview image display
    const reader = new FileReader();
    reader.readAsDataURL(fileToUpload);  
    reader.onload = event => {
    this.editForm.get(type).setValue(event.target?.result);
    this.editForm.get(['urlVideo'])!.setErrors({});
    this.editForm.get(['urlVideo'])!.updateValueAndValidity();
    };
    }else {
      this.editForm.get(['urlVideo'])!.setErrors({ incorrect: true });
      this.incorrect = true;
    }
    
  }
  
  }


  getPathByTypeMedia(typeMedia: string){
    const extensionMapping: { [key: string]: string } = {
      mp3: 'audios',
      mp4: 'videos',
      jpg: 'images',
      jpeg: 'images',
      png: 'images',
      pdf: 'pdfs',
    };
    const fileType = extensionMapping[typeMedia];
    return fileType 
    
  }
  protected createFromForm(): FdlvVideo {
    return {
      ...new FdlvVideo(),
      id: this.editForm.get(['id'])!.value,
      theme: this.editForm.get(['theme'])!.value,
      numOrdre: this.editForm.get(['numOrdre'])!.value,
      nomVideo: this.editForm.get(['nomVideo'])!.value,
      urlVideo: this.editForm.get(['urlVideo'])!.value,
      typeMedia: this.editForm.get(['typeMedia'])!.value,
      urlImage: this.editForm.get(['urlImage'])!.value,
      description: this.editForm.get(['description'])!.value,
      active: this.editForm.get(['active'])!.value === undefined ? false : this.editForm.get(['active'])!.value,
      flvFusId: this.editForm.get(['flvFusId'])!.value,
      flvMediaValide: this.editForm.get(['flvMediaValide'])!.value,
    };
  }


  getMediaType(filename:string){
    const typeMedia = filename.split('.').pop();
    return "."+typeMedia;
  }

  protected getThemeByID(themeId?: number): ITheme {
    if (this.themeList === undefined) {
      return { ...new Theme() };
    }
    return this.themeList.filter(t => t.id === themeId)[0];
  }

  get id(): number {
    return this.editForm.get(['id'])!.value as number;
  }

  onChange(value: any) {
    this.urlVideo = null;
    this.editForm.patchValue({
      urlVideo: null
    });
  }

  mediaUrl() {
    return this.sanitizer.bypassSecurityTrustUrl(<string>this.urlVideo);
  }

  pdfUrl() {
    return this.sanitizer.bypassSecurityTrustResourceUrl(<string>this.urlVideo);
  }
}
