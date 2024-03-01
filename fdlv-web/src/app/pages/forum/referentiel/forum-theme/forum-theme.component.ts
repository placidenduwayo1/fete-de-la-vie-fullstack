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
import { ForumTheme } from 'src/app/model/forum/forum-theme.model'
import { ForumThemeService } from 'src/app/services/forum/forum-theme.service'

@Component({
  selector: 'app-theme-forum',
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
  templateUrl: './forum-theme.component.html',
  styleUrls: ['./forum-theme.component.scss']
})
export class ForumThemeComponent implements OnInit {
  @ViewChild('myTab', { static: true }) myTab: Table
  forumTheme: ForumTheme[] = []
  data: any = []
  displayAddForm = false
  displayEditForm = false
  displayDeleteForm = false;
  datauser = {
    id: null,
    code: '',
    libelle: '',
    commentaire: ''
  }
  title: any
  errormessage: boolean = false
  idToDelete: number = 0
  searchValue: string = ''
  constructor (
    private searchService: SearchServiceService,
    private forumThemeService: ForumThemeService,
    private route: Router,
    private messageService : MessageService
  ) {}

  ngOnInit () {
    this.forumThemeService
      .getAllForumTheme()
      .subscribe((response: any = []) => {
        this.data = response;
        this.forumTheme = response;
        // Récupérer la valeur de recherche au chargement de la page
        this.searchValue = this.searchService.getSearchValue();
        // Appliquer le filtre global avec la valeur récupérée
        this.myTab.filterGlobal(this.searchValue, 'contains');
      })
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
    this.errormessage = false;
    this.displayAddForm = true;
  }

  closeAddForm () {
    this.displayAddForm = false;
  }

  openEditForm (theme : any) {
    this.errormessage = false;
    this.datauser = theme;
    this.displayEditForm = true;
    console.log('role', this.datauser)
  }

  closeEditForm () {
    this.displayEditForm = false;
  }

  saveForm (forumTheme: any) {
    if(!this.validateForm(forumTheme)){
      return;
    }

    this.forumThemeService.createForumTheme(forumTheme).subscribe(
      response => {
        const message = {
          severity: 'success',
          summary: 'Création d\'un thème forum',
          detail: 'Le thème ' + forumTheme.code + ' a été créé avec succèss.',
        }
        this.messageService.add(message);
        this.displayAddForm = false;
        this.ngOnInit ();
      },
      (error: HttpErrorResponse) => {
        const message = {
          severity: 'error',
          summary: 'Création d\'un thème forum',
          detail: 'Erreur lors de la création d\'un thème: ' + error.error.title
        }
        this.messageService.add(message);
        this.displayAddForm = false;
      }
    )
  }

  validateForm(forumTheme: any) : boolean {
    if(forumTheme.code == "" || forumTheme.code.length > 10){
      this.title = "Le code thème est obligatoire et contient moins de 10 caractères."
      this.errormessage = true;
      return false;
    }
    if(forumTheme.libelle == ""){
      this.title = "Le libellé est obligatoire."
      this.errormessage = true;
      return false;
    } 
    return true;
  }

  editForm (forumTheme: any) {
    if(!this.validateForm(forumTheme)){
      return;
    }
    this.forumThemeService.updateForumTheme(forumTheme)
      .subscribe(
        response => {
          console.log(response);
          this.messageService.add({
            severity: 'success',
            summary: 'Modification d\'un thème forum.',
            detail: 'Le thème forum a été modifié.',
          });
          this.closeEditForm();
          this.ngOnInit();
        },
        (error: HttpErrorResponse) => {
          const message = {
            severity: 'error',
            summary: 'Modification d\'un thème forum',
            detail: 'Erreur lors de la modification: ' + error.error.title
          }
          this.closeEditForm();
        }
      )
  }

  deleteThemeForum() {
    this.forumThemeService
      .deleteForumTheme(this.idToDelete)
      .subscribe(response => {
        const message = {
          severity: 'success',
          summary: 'Suppression d\'un thème forum',
          detail: 'Ce thème forum a été supprimé avec succèss.',
        }
        this.messageService.add(message);
        this.closeConfirmationDialog();
      },
      (error: HttpErrorResponse) => {
        const message = {
          severity: 'error',
          summary: 'Suppression d\'un thème forum',
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
      this.route.navigate(['forum/referentiel/forum-theme'])
    });
  }
}

