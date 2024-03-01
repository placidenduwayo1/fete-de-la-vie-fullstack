import { Component, OnInit, ViewChild } from '@angular/core'
import { CommonModule } from '@angular/common'
import { PanelModule } from 'primeng/panel'
import { TableModule } from 'primeng/table'
import { FormsModule } from '@angular/forms'
import { Table } from 'primeng/table'
import { ToastModule } from 'primeng/toast';
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
import { RoleStructureService } from 'src/app/services/forum/role-structure.service'
import { RoleStructure } from '../../../../model/forum/role-structure.model'
import { SearchServiceService } from 'src/app/services/forum/search-service.service'
import { MessageService } from 'primeng/api'

@Component({
  selector: 'app-role-structure',
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
  templateUrl: './role-structure.component.html',
  styleUrls: ['./role-structure.component.scss']
})
export class RoleStructureComponent implements OnInit {
  @ViewChild('myTab', { static: true }) myTab: Table
  RoleStructure: RoleStructure[] = []
  data: any = []
  class: any
  displayAddForm = false
  displayEditForm = false
  displayDeleteForm: boolean = false
  datauser = {
    id: 0,
    codeRoleStructure: '',
    roleStructure: ''
  }
  title: any
  selectedRs : RoleStructure
  selectedValues: string[] = ['ROLE_ADMIN', 'ROLE_USER']
  errormessage : boolean = false;
  searchValue: string = ''
  constructor (
    private searchService: SearchServiceService,
    private roleStructureService: RoleStructureService,
    private route: Router,
    private messageService : MessageService
  ) {}

  ngOnInit () {
    this.errormessage = false;
    this.roleStructureService
      .getAllRoleStructures()
      .subscribe((response: any = []) => {
        this.data = response
        this.RoleStructure = response
        // Récupérer la valeur de recherche au chargement de la page
        this.searchValue = this.searchService.getSearchValue()
        // Appliquer le filtre global avec la valeur récupérée
        this.myTab.filterGlobal(this.searchValue, 'contains')
  })}

  onSearchInputChange (event: any): void {
    const newValue = event.target.value
    this.searchValue = newValue
    // Sauvegarder la nouvelle valeur de recherche
    this.searchService.saveSearchValue(newValue)
    // Appliquer le filtre global avec la nouvelle valeur
    this.myTab.filterGlobal(newValue, 'contains')
  }

  getStatusClass (statut: any): string {
    if (statut === false) {
      return 'p-button-danger'
    } else {
      return 'p-button-success'
    }
  }

  getStatusLabel (statut: any): string {
    if (statut === false) {
      return 'Désactiver'
    } else {
      return 'Activer'
    }
  }

  openAddForm() {
    this.errormessage = false;
    this.displayAddForm = true;
  }

  closeForm () {
    this.displayAddForm = false;
  }

  openEditForm (datauser: any) {
    this.errormessage = false;
    this.datauser.id = datauser.froId
    this.datauser.codeRoleStructure = datauser.froCode
    this.datauser.roleStructure = datauser.froRole
    this.displayEditForm = true
    console.log(this.datauser)
  }

  closeEditForm () {
    this.displayEditForm = false;
  }

  saveForm (rs: any) {
    if(!this.validateForm(rs)){
      return;
    }
    this.roleStructureService.createRoleStructure(rs).subscribe(
      response => {
        const message = {
          severity: 'success',
          summary: 'Création d\'un rôle structure',
          detail: 'Rôle structure ' + response.froRole + ' a été créé avec succèss.',
        }
        this.messageService.add(message);
        this.displayAddForm = false;
        this.ngOnInit ();
      },
      (error: HttpErrorResponse) => {
        const message = {
          severity: 'error',
          summary: 'Création d\'un rôle structure',
          detail: 'Erreur lors de la création d\'un rôle structure: ' + error.error.title
        }
        this.messageService.add(message);
        this.displayAddForm = false;
      }
    )
  }

  editForm (rs: any) {
    if(!this.validateForm(rs)){
      return;
    }
    this.roleStructureService
      .updateRoleStructure(this.datauser.id, rs)
      .subscribe(
        response => {
          console.log(response);
          this.messageService.add({
            severity: 'success',
            summary: 'Modification d\'un rôle structure.',
            detail: 'Le rôle structure a été modifié.',
          });
          this.closeEditForm();
          this.ngOnInit();
        },
        (error: HttpErrorResponse) => {
          const message = {
            severity: 'error',
            summary: 'Modification d\'un rôle structure',
            detail: 'Erreur lors de la modification d\'un rôle structure: ' + error.error.title
          }
          this.closeEditForm();
        }
      )
  }

  deleteRoleStructure () {
    this.roleStructureService.deleteRoleStructure(this.selectedRs.froId).subscribe(
      response => {
        const message = {
          severity: 'success',
          summary: 'Suppression d\'un rôle structure',
          detail: 'Rôle structure ' + this.selectedRs.froRole + ' a été supprimé.',
        }
        this.messageService.add(message);
        this.closeConfirmationDialog();
        this.ngOnInit();
      },
      (error: HttpErrorResponse) => {
        const message = {
          severity: 'error',
          summary: 'Suppression d\'un rôle structure',
          detail: 'Erreur lors de la suppression de ce rôle structure: ' + error.error.title
        };
        this.closeConfirmationDialog();
        this.ngOnInit();
      }
    )
  }

  openConfirmationDialog (rs : any) {
    this.errormessage = false;
    this.selectedRs = rs;
    this.displayDeleteForm = true;
  }

  closeConfirmationDialog() {
    this.selectedRs = null;
    this.displayDeleteForm = false;
  }

  reloadPage(){
    this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.route.navigate(['forum/referentiel/role-structure']);
    })
  }

  first = Number(localStorage.getItem("pageRoleStructure"))  ;
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageRoleStructure", this.first.toString());
  }

  validateForm(rolestructure: any) : boolean {
    if(rolestructure.froCode == "" || rolestructure.froCode.length > 10){
      this.title = "Le code structure est obligatoire et contient moins de 10 caractères."
      this.errormessage = true;
      return false;
    }
    if(rolestructure.froRole == ""){
      this.title = "Le détail est obligatoire."
      this.errormessage = true;
      return false;
    } 
    return true;
  }

}
