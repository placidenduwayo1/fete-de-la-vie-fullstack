import { Component, OnInit, ViewChild } from '@angular/core'
import { CommonModule } from '@angular/common'
import { PanelModule } from 'primeng/panel'
import { TableModule } from 'primeng/table'
import { Table } from 'primeng/table'

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
import { HttpErrorResponse } from '@angular/common/http'
import { ModuleApplicatifService } from 'src/app/services/forum/module-applicatif.service'
import { ModuleApplicatif } from 'src/app/model/forum/module-applicatif.model'
import { SearchServiceService } from 'src/app/services/forum/search-service.service'

@Component({
  selector: 'app-modules-forums',
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
    MessageModule
  ],
  templateUrl: './modules-forums.component.html',
  styleUrls: ['./modules-forums.component.scss']
})
export class ModulesForumsComponent implements OnInit {
  @ViewChild('myTab', { static: true }) myTab: Table

  moduleForum: ModuleApplicatif[] = []
  user: any
  data: any = []
  class: any
  label: any
  display = false
  displayEditForm = false
  datauser = {
    id: 0,
    codeModule: '',
    module: ''
  }
  title: any
  errormessage: boolean = false
  random_password = ''
  generatedPassword: any

  selectedValues: string[] = ['ROLE_ADMIN', 'ROLE_USER']
  showConfirmationDialog: boolean = false
  idToDelete: number = 0

  searchValue: string = ''

  constructor (
    private searchService: SearchServiceService,
    private ModuleService: ModuleApplicatifService,
    private route: Router
  ) {}

  ngOnInit () {
    this.ModuleService.getAllModuleApplicatifs().subscribe(
      (response: any = []) => {
        this.data = response
        this.moduleForum = response
        // Récupérer la valeur de recherche au chargement de la page
        this.searchValue = this.searchService.getSearchValue()

        // Appliquer le filtre global avec la valeur récupérée
        this.myTab.filterGlobal(this.searchValue, 'contains')
      }
    )
  }
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

  openForm () {
    this.display = true
  }

  closeForm () {
    this.display = false
  }
  openEditForm (id: number, codeModule: any, module: any) {
    this.datauser.id = id
    this.datauser.codeModule = codeModule
    this.datauser.module = module
    this.displayEditForm = true
    console.log('role', this.datauser)
  }

  closeEditForm () {
    this.displayEditForm = false
  }
  saveForm (moduleForum: any) {
    this.ModuleService.createModuleApplicatif(moduleForum).subscribe(
      response => {
        console.log(response)
        this.reloadPage()
      },
      (error: HttpErrorResponse) => {
        console.log('Error title:', error.error.title)
        this.title = error.error.title
        this.errormessage = true
      }
    )
  }

  EditForm (moduleForum: any) {
    console.log('jjjjjjjjjjjj', this.datauser.id, moduleForum, 'hhhhhhhhhhhh')
    this.ModuleService.updateModuleApplicatif(
      this.datauser.id,
      moduleForum
    ).subscribe(
      response => {
        console.log(response)
        this.reloadPage()
      },
      (error: HttpErrorResponse) => {
        console.log('Error title:', error.error.title)
        this.title = error.error.title
        this.errormessage = true
      }
    )
  }

  delete (id: any) {
    this.ModuleService.deleteModuleApplicatif(id).subscribe(response => {
      this.closeConfirmationDialog()
      this.reloadPage()
    })
  }

  openConfirmationDialog (id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = true
  }

  closeConfirmationDialog () {
    this.showConfirmationDialog = false
  }
  loadAll () {}
  reloadPage () {
    const currentUrl = this.route.url
    this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.route.navigate(['forum/referentiel/modules-forums'])
    })
  }
}
