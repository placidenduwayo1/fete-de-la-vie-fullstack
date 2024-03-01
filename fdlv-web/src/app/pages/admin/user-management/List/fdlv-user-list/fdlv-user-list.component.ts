import { Component, OnInit, ViewChild } from '@angular/core'
import { TagModule } from 'primeng/tag'
import { CommonModule } from '@angular/common'
import { PanelModule } from 'primeng/panel'
import { TableModule } from 'primeng/table'
import { FormsModule } from '@angular/forms'
import { InputTextModule } from 'primeng/inputtext'
import { NavbarComponent } from 'src/app/ui/navbar/navbar.component'
import { Router, RouterOutlet } from '@angular/router'
import { CardModule } from 'primeng/card'
import { ButtonModule } from 'primeng/button'
import { RippleModule } from 'primeng/ripple'
import { UserManagementService } from '../../Service/user-management.service'
import { DialogModule } from 'primeng/dialog'
import { CheckboxModule } from 'primeng/checkbox'
import { HttpErrorResponse, HttpResponse } from '@angular/common/http'
import { MessageModule } from 'primeng/message'
import { ToastModule } from 'primeng/toast'
import { IUser, User } from '../../../../../model/user.model'
import { Table } from 'primeng/table'

import { SearchServiceService } from 'src/app/services/forum/search-service.service'
import { first } from 'rxjs'
import { AlertService } from 'src/app/shared/alert/alert.service'
import { AlertSeverity } from 'src/app/enums/alert-severity.enum'

@Component({
  selector: 'app-fdlv-user-list',
  standalone: true,
  imports: [
    CommonModule,
    ToastModule,
    PanelModule,
    TableModule,
    FormsModule,
    InputTextModule,
    NavbarComponent,
    RouterOutlet,
    CardModule,
    TagModule,
    ButtonModule,
    RippleModule,
    DialogModule,
    CheckboxModule,
    MessageModule
  ],
  templateUrl: './fdlv-user-list.component.html',
  styleUrls: ['./fdlv-user-list.component.scss']
})
export class FdlvUserListComponent implements OnInit {
  @ViewChild('myTab', { static: true }) myTab: Table

  user: any
  data: IUser[] = []
  class: any
  label: any
  display = false
  displayEditForm = false
  datauser = {
    id: '',
    email: '',
    login: '',
    firstName: '',
    lastName: '',
    password: '',
    activated: null
  }
  title: any
  errormessage: boolean = false
  random_password = ''
  generatedPassword: any

  selectedValues: string[] = ['ROLE_ADMIN', 'ROLE_USER']
  showConfirmationDialog: boolean = false
  loginToDelete: string = ''
  isChampDesactive: boolean = true
  // Dans votre composant TypeScript
  isPasswordBlockVisible: boolean = true

  searchValue: string = ''

  constructor (
    private searchService: SearchServiceService,
    private UserManagementService: UserManagementService,
    private route: Router,
    private alertService : AlertService
  ) {}

  ngOnInit () {
    this.UserManagementService.findAll().subscribe((response: HttpResponse<IUser[]>)=>{
      this.data = response.body;
      console.log(this.data)
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
  openEditForm (
    id: any,
    login: any,
    firstName: any,
    lastName: any,
    email: any,
    activated: any
  ) {
    this.datauser.id = id
    this.datauser.email = email
    this.datauser.login = login
    this.datauser.firstName = firstName
    this.datauser.lastName = lastName
    this.datauser.password = ''
    this.displayEditForm = true
    this.datauser.activated = activated

    console.log('user ', this.datauser)
  }

  closeEditForm () {
    this.displayEditForm = false
  }

  saveForm (user: any) {
    this.UserManagementService.create(user).pipe(first()).subscribe({
     next: response => {
        this.reloadPage();
        this.alertService.addMessage({ severity: AlertSeverity.SUCCESS, summary: 'Création de compte', detail: "Le compte a été créé." });
      },
      error:(error: HttpErrorResponse) => {
        this.reloadPage();
        this.alertService.addMessage({ severity: AlertSeverity.ERROR, summary: 'Création de compte', detail: error.error.title });
      }
  })
  }

  EditUser (user: any) {
    this.UserManagementService.updateUserInformation(
      this.datauser.login,
      user
    ).pipe(first()).subscribe({
      next:response => {
        this.reloadPage();
        this.alertService.addMessage({ severity: AlertSeverity.SUCCESS, summary: 'Modification', detail: "Le compte a été modifié." });
      },
      error:(error: HttpErrorResponse) => {
        this.reloadPage();
        this.alertService.addMessage({ severity: AlertSeverity.ERROR, summary: 'Modification', detail: error.error.title });
    
      }
    }
    )
  }
  public randompwd (): string {
    //this.clicked_event = true;
    this.random_password = Math.random().toString(36).slice(-8)
    this.generatedPassword = this.random_password
    return this.random_password
  }

  deleteUser () {
    this.UserManagementService.delete(this.loginToDelete).pipe(first()).subscribe(
      {
      next:response => {
      this.closeConfirmationDialog()
      this.alertService.addMessage({ severity: AlertSeverity.SUCCESS, summary: 'Suppression', detail: "Le compte a été supprimé." });
      this.reloadPage()
    },
    error:(error: HttpErrorResponse) => {
      this.alertService.addMessage({ severity: AlertSeverity.ERROR, summary: 'Suppression', detail: "Le compte n'a  pas pu être supprimé." });
    }
  })
  }

  openConfirmationDialog (login: string) {
    this.loginToDelete = login;
    this.showConfirmationDialog = true
  }

  closeConfirmationDialog () {
    this.showConfirmationDialog = false
  }

  reloadPage () {
    this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.route.navigate(['user-management'])
    })
  }
  setActive (user: User, isActivated: boolean): void {
    this.UserManagementService.update({
      ...user,
      activated: isActivated
    }).subscribe(() => this.reloadPage())
  }

  first = Number(localStorage.getItem("pageUserList"))  ;
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageUserList", this.first.toString());
}
}
