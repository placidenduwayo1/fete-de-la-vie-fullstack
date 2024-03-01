import { Component, OnInit, ViewChild } from '@angular/core'
import { CommonModule } from '@angular/common'
import { PanelModule } from 'primeng/panel'
import { TableModule } from 'primeng/table'
import { FormsModule } from '@angular/forms'
import { ToastModule } from 'primeng/toast'
import { Table } from 'primeng/table'
import { InputTextModule } from 'primeng/inputtext'
import { NavbarComponent } from 'src/app/ui/navbar/navbar.component'
import { Router, RouterOutlet } from '@angular/router'
import { CardModule } from 'primeng/card'
import { ButtonModule } from 'primeng/button'
import { RippleModule } from 'primeng/ripple'
import { DialogModule } from 'primeng/dialog'
import { CheckboxModule } from 'primeng/checkbox'
import { MessageModule } from 'primeng/message'
import { HttpErrorResponse } from '@angular/common/http'
import { DropdownModule } from 'primeng/dropdown'
import { SearchServiceService } from 'src/app/services/forum/search-service.service'
import { MessageService } from 'primeng/api'
import { Banniere } from 'src/app/model/forum/banniere.model'
import { BanniereService } from 'src/app/services/forum/banniere.service'
import { ForumThemeService } from 'src/app/services/forum/forum-theme.service'
import { ForumTheme } from 'src/app/model/forum/forum-theme.model'

@Component({
  selector: 'app-banniere',
  standalone: true,
  imports: [
    DropdownModule,
    CommonModule,
    PanelModule,
    TableModule,
    FormsModule,
    InputTextModule,
    NavbarComponent,
    RouterOutlet,
    CardModule,
    ButtonModule,
    RippleModule,
    DialogModule,
    CheckboxModule,
    MessageModule,
    ToastModule
  ],
  providers: [
    MessageService
  ],
  templateUrl: './banniere.component.html',
  styleUrls: ['./banniere.component.scss']
})
export class BanniereComponent implements OnInit {

  @ViewChild('myTab', { static: true }) myTab: Table
  listBanniere: Banniere[] = [];
  listForumTheme: ForumTheme[] = [];
  listFdlv: [];
  displayAddForm = false
  displayEditForm = false
  displayDeleteForm = false;
  datauser = {
    id: null,
    libelle: '',
    code: '',
    forumTheme: '',
    forumThemeId: null,
    url:'',
    fdlv:'',
    commentaire: ''
  }
  title: any
  errormessage: boolean = false
  idToDelete: number = 0
  searchValue: string = ''
  constructor (
    private searchService: SearchServiceService,
    private banniereService: BanniereService,
    private forumThemeService: ForumThemeService,
    private route: Router,
    private messageService : MessageService
  ) {}

  ngOnInit () {
    this.forumThemeService
    .getAllForumTheme()
    .subscribe(
      (response: any = []) => {
        this.listForumTheme = response;
        this.banniereService.getAllBanniere().subscribe(
          (response: any = []) => {
            this.listBanniere = this.editListBanniere(response);
            this.searchValue = this.searchService.getSearchValue();
            this.myTab.filterGlobal(this.searchValue, 'contains');
          },
          (error : HttpErrorResponse) => {
            const message = {
            severity: 'error',
            summary: 'Loading Page Error',
            detail: 'Récupération Liste des bannières.'
            }
            this.messageService.add(message);
          });
      },
      (error : HttpErrorResponse) => {
        const message = {
          severity: 'error',
          summary: 'Loading Page Error',
          detail: 'Erreur lors de la récupération de la liste des bannières.'
        }
        this.messageService.add(message);
      });
  }

  editListBanniere(listRaw : any){
    return listRaw.map(banniere => {
      return {
        id: banniere.id,
        code: banniere.code,
        libelle : banniere.libelle,
        forumTheme: this.listForumTheme.find(i => i.id === banniere.forumThemeId)?.libelle,
        fdlv: banniere.fdlv,
        commentaire : banniere.commentaire,
        url: banniere.url
      }
    });
  }

  onSearchInputChange (event: any): void {
    const newValue = event.target.value
    this.searchValue = newValue

    // Sauvegarder la nouvelle valeur de recherche
    this.searchService.saveSearchValue(newValue)

    // Appliquer le filtre global avec la nouvelle valeur
    this.myTab.filterGlobal(newValue, 'contains')
  }

  openAddForm() {
    this.route.navigateByUrl('/forum/referentiel/banniere-modif');
  }

  closeAddForm () {
    this.displayAddForm = false;
  }

  openEditForm (banniere : any) {
    this.route.navigate(['/forum/referentiel/banniere-modif'], { state: { data: banniere , type:'modification'} });
  }

  closeEditForm() {
    this.displayEditForm = false;
  }

  saveForm(banniere: any) {
    if(!this.validateForm(banniere)){
      return;
    }

    this.banniereService.createBanniere(banniere).subscribe(
      response => {
        const message = {
          severity: 'success',
          summary: 'Création d\'un bannière',
          detail: 'Le thème ' + banniere.code + ' a été créé avec succèss.',
        }
        this.messageService.add(message);
        this.displayAddForm = false;
        this.ngOnInit ();
      },
      (error: HttpErrorResponse) => {
        const message = {
          severity: 'error',
          summary: 'Création d\'un bannière',
          detail: 'Erreur lors de la création d\'un bannière: ' + error.error.title
        }
        this.messageService.add(message);
        this.displayAddForm = false;
      }
    )
  }

  validateForm(banniere: any) : boolean {
    if(banniere.code == "" || banniere.code.length > 10){
      this.title = "Le code bannière est obligatoire et contient moins de 10 caractères."
      this.errormessage = true;
      return false;
    }
    if(banniere.libelle == ""){
      this.title = "Le libellé est obligatoire."
      this.errormessage = true;
      return false;
    } 
    return true;
  }

  deleteBanniere() {
    this.banniereService
      .deleteBanniere(this.idToDelete)
      .subscribe(response => {
        const message = {
          severity: 'success',
          summary: 'Suppression d\'un bannière',
          detail: 'Ce bannière a été supprimé avec succèss.',
        }
        this.messageService.add(message);
        this.closeConfirmationDialog();
      },
      (error: HttpErrorResponse) => {
        const message = {
          severity: 'error',
          summary: 'Suppression d\'un bannière',
          detail: 'Erreur lors de la suppression: ' + error.error.title
        }
        this.messageService.add(message);
        this.closeConfirmationDialog();
      })
  }

  openDeleteForm (theme: any) {
    this.errormessage = false;
    this.idToDelete = theme.id;
    this.displayDeleteForm = true;
  }

  closeConfirmationDialog () {
    this.displayDeleteForm = false;
    this.ngOnInit();
  }

  reloadPage () {
    this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.route.navigate(['forum/referentiel/banniere'])
    });
  }

  openFile(url: string) {
    window.open(url, "_blank");
  }
}