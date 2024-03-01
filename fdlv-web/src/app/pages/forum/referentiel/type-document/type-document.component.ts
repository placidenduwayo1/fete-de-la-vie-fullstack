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
import { DocumentTypeService } from 'src/app/services/forum/document-type.service'
import { SearchServiceService } from 'src/app/services/forum/search-service.service'
import { DocumentType } from '../../../../model/forum/document-type.model'

@Component({
  selector: 'app-type-document',
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
  templateUrl: './type-document.component.html',
  styleUrls: ['./type-document.component.scss']
})
export class TypeDocumentComponent implements OnInit {
  @ViewChild('myTab', { static: true }) myTab: Table
  documentType: DocumentType[] = []
  user: any
  data: any = []
  class: any
  label: any
  display = false
  displayEditForm = false
  datauser = {
    id: 0,
    codeTypeDoc: '',
    typeDoc: ''
  }
  title: any
  errormessage: boolean = false
  showConfirmationDialog: boolean = false
  idToDelete: number = 0
  searchValue: string = ''

  constructor (
    private searchService: SearchServiceService,
    private documentTypeService: DocumentTypeService,

    private route: Router
  ) {}

  ngOnInit () {
    this.documentTypeService.getAllDocumentTypes().subscribe(
      (response: DocumentType[] = []) => {
        this.data = response
        this.documentType = response
        // Récupérer la valeur de recherche au chargement de la page
        this.searchValue = this.searchService.getSearchValue()

        // Appliquer le filtre global avec la valeur récupérée
        this.myTab.filterGlobal(this.searchValue, 'contains')
      },
      (error: any) => {
        // Handle errors
      }
    )
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
  onSearchInputChange (event: any): void {
    const newValue = event.target.value
    this.searchValue = newValue

    // Sauvegarder la nouvelle valeur de recherche
    this.searchService.saveSearchValue(newValue)

    // Appliquer le filtre global avec la nouvelle valeur
    this.myTab.filterGlobal(newValue, 'contains')
  }

  openForm () {
    this.display = true
  }

  closeForm () {
    this.display = false

    this.searchValue = '' // Réinitialisez la valeur de recherche à une chaîne vide
    this.myTab.filterGlobal('', 'contains') // Appliquez le filtre global pour effacer le filtre actuel
  }
  openEditForm (id: number, codeTypeDoc: any, typeDoc: any) {
    this.datauser.id = id
    this.datauser.codeTypeDoc = codeTypeDoc
    this.datauser.typeDoc = typeDoc
    this.displayEditForm = true
    console.log(this.datauser)
  }

  closeEditForm () {
    this.displayEditForm = false
  }
  saveForm (RoleStructure: any) {
    this.documentTypeService.createDocumentType(RoleStructure).subscribe(
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

  EditForm (RoleStructure: any) {
    console.log('jjjjjjjjjjjj', this.datauser.id, RoleStructure, 'hhhhhhhhhhhh')
    this.documentTypeService
      .updateDocumentType(this.datauser.id, RoleStructure)

      .subscribe(
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
    this.documentTypeService.deleteDocumentType(id).subscribe(response => {
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
      this.route.navigate(['forum/referentiel/type-document'])
    })
  }

  first = Number(localStorage.getItem("pageTypeDoc"))  ;
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageTypeDoc", this.first.toString());
}
}
