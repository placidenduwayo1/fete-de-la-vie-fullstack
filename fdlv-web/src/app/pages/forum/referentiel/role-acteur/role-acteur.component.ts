import { RoleActeur } from './../../../../model/forum/role-acteur.model'
import { Component, OnInit, ViewChild } from '@angular/core'
import { CommonModule } from '@angular/common'
import { PanelModule } from 'primeng/panel'
import { Table } from 'primeng/table'
import { ToastModule } from 'primeng/toast';
import { TableModule } from 'primeng/table'
import { FormsModule } from '@angular/forms'
import { InputTextModule } from 'primeng/inputtext'
import { NavbarComponent } from 'src/app/ui/navbar/navbar.component'
import { Router, RouterOutlet } from '@angular/router'
import { CardModule } from 'primeng/card'
import { ButtonModule } from 'primeng/button'
import { RippleModule } from 'primeng/ripple'
import { DialogModule } from 'primeng/dialog'
import { CheckboxModule } from 'primeng/checkbox'
import { MessageModule } from 'primeng/message'
import { RoleActeurService } from 'src/app/services/forum/role-acteur.service'
import { HttpErrorResponse } from '@angular/common/http'
import { SearchServiceService } from 'src/app/services/forum/search-service.service'
import { MessageService } from 'primeng/api'

@Component({
  selector: 'app-role-acteur',
  standalone: true,
  imports: [
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
  templateUrl: './role-acteur.component.html',
  styleUrls: ['./role-acteur.component.scss']
})
export class RoleActeurComponent implements OnInit {
  @ViewChild('myTab', { static: true }) myTab: Table
  roleActeur: RoleActeur[] = []
  data: any = []
  displayAddForm = false
  displayEditForm = false
  displayDeleteForm = false;
  datauser = {
    idRoleActeur: '',
    codeRoleActeur: '',
    roleActeur: ''
  }
  title: any
  errormessage: boolean = false
  idToDelete: number = 0
  searchValue: string = ''
  constructor (
    private searchService: SearchServiceService,
    private roleActeurService: RoleActeurService,
    private route: Router,
    private messageService : MessageService
  ) {}

  ngOnInit () {
    this.roleActeurService
      .getAllRoleActeurs()
      .subscribe((response: any = []) => {
        this.data = response
        this.roleActeur = response
        // Récupérer la valeur de recherche au chargement de la page
        this.searchValue = this.searchService.getSearchValue()

        // Appliquer le filtre global avec la valeur récupérée
        this.myTab.filterGlobal(this.searchValue, 'contains')
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

  openEditForm (id: any, codeRoleActeur: any, roleActeur: any) {
    this.errormessage = false;
    this.datauser.idRoleActeur = id
    this.datauser.codeRoleActeur = codeRoleActeur
    this.datauser.roleActeur = roleActeur
    this.displayEditForm = true
    console.log('role', this.datauser)
  }

  closeEditForm () {
    this.displayEditForm = false;
  }

  saveForm (roleActeur: any) {
    if(!this.validateForm(roleActeur)){
      return;
    }

    this.roleActeurService.create(roleActeur).subscribe(
      response => {
        const message = {
          severity: 'success',
          summary: 'Création d\'un rôle acteur',
          detail: 'Rôle structure ' + roleActeur.roleActeur + ' a été créé avec succèss.',
        }
        this.messageService.add(message);
        this.displayAddForm = false;
        this.ngOnInit ();
      },
      (error: HttpErrorResponse) => {
        const message = {
          severity: 'error',
          summary: 'Création d\'un rôle acteur',
          detail: 'Erreur lors de la création d\'un rôle structure: ' + error.error.title
        }
        this.messageService.add(message);
        this.displayAddForm = false;
      }
    )
  }

  validateForm(roleActeur: any) : boolean {
    if(roleActeur.codeRoleActeur == "" || roleActeur.codeRoleActeur.length > 10){
      this.title = "Le code acteur est obligatoire et contient moins de 10 caractères."
      this.errormessage = true;
      return false;
    }
    if(roleActeur.roleActeur == ""){
      this.title = "Le rôle acteur est obligatoire."
      this.errormessage = true;
      return false;
    } 
    return true;
  }

  editForm (roleActeur: any) {
    if(!this.validateForm(roleActeur)){
      return;
    }
    this.roleActeurService
      .updateRoleActeur(this.datauser.idRoleActeur, roleActeur)
      .subscribe(
        response => {
          console.log(response);
          this.messageService.add({
            severity: 'success',
            summary: 'Modification d\'un rôle acteur.',
            detail: 'Le rôle acteur a été modifié.',
          });
          this.closeEditForm();
          this.ngOnInit();
        },
        (error: HttpErrorResponse) => {
          const message = {
            severity: 'error',
            summary: 'Modification d\'un rôle acteur',
            detail: 'Erreur lors de la modification d\'un rôle acteur: ' + error.error.title
          }
          this.closeEditForm();
        }
      )
  }

  deleteRoleActeur() {
    this.roleActeurService
      .deleteRoleActeur(this.idToDelete)
      .subscribe(response => {
        const message = {
          severity: 'success',
          summary: 'Suppression d\'un rôle acteur',
          detail: 'Rôle structure a été supprimé avec succèss.',
        }
        this.messageService.add(message);
        this.closeConfirmationDialog();
      },
      (error: HttpErrorResponse) => {
        console.log('Error title:', error.error.title)
        this.title = "Attention, ce rôle est défini dans les autres services.";
        this.errormessage = true;
      })
  }

  openDeleteForm (id: number) {
    this.errormessage = false;
    this.idToDelete = id
    this.displayDeleteForm = true;
  }

  closeConfirmationDialog () {
    this.displayDeleteForm = false;
    this.ngOnInit();
  }

  reloadPage () {
    this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.route.navigate(['forum/referentiel/role-acteur'])
    });
  }

  first = Number(localStorage.getItem("pageRoleUser"))  ;
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageRoleUser", this.first.toString());
}
}
