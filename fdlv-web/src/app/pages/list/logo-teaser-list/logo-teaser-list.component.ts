import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogoTeaser } from 'src/app/model/logo-teaser.model';
import { LogoTeaserService } from 'src/app/services/logo-teaser.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { FdlvUsersService } from 'src/app/services/fdlv-users.service';
import { first, firstValueFrom } from 'rxjs';
import { ToastModule } from 'primeng/toast';

import { TagModule } from 'primeng/tag'
import { PanelModule } from 'primeng/panel'
import { Table, TableModule } from 'primeng/table'
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
import { FdlvUsers } from 'src/app/model/fdlv-user.model';
import { LogoTeaserModalComponent } from './logo-teaser-modal/logo-teaser-modal.component';
import { ModalTypeEnum } from 'src/app/enums/modal-type.enum';
import { DialogDeleteComponent } from '../../shared/dialog-delete/dialog-delete.component';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { AlertService } from 'src/app/shared/alert/alert.service';

@Component({
  selector: 'gmd-logo-teaser-list',
  standalone: true,
  imports: [CommonModule,
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
    MessageModule,
    LogoTeaserModalComponent,
    DialogDeleteComponent],
  templateUrl: './logo-teaser-list.component.html',
  styleUrls: ['./logo-teaser-list.component.scss']
})
export class LogoTeaserListComponent {
  @ViewChild(LogoTeaserModalComponent) modal!: LogoTeaserModalComponent;
  logoteasers?: LogoTeaser[];
  isLoading = false;
  dtOptions: any;
  title?: string;
  idToDelete: number;
  showConfirmationDialog: boolean;
  storedSearchValue = "";
  @ViewChild('myTabM', { static: true }) myTab: Table;
  constructor(protected logoTeaserService: LogoTeaserService,private alertService : AlertService, protected userService: FdlvUsersService, private route: Router, private fdlvUserService: FdlvUsersService) { }

  async loadAll(): Promise<void> {
    this.isLoading = true;

    const res: HttpResponse<LogoTeaser[]> = await firstValueFrom(this.logoTeaserService.findAll());
    this.logoteasers = res.body ?? [];

    const userLoginPromises = this.logoteasers
      .map(logoteaser => logoteaser.rltFusId)
      .filter(fusId => fusId !== undefined)
      .map(fusId => this.userService.getUserLogin(<number>fusId));
    const userLogins = await Promise.all(userLoginPromises);
    this.logoteasers.forEach((logoteaser, index) => {
      logoteaser.rltFusId = logoteaser.rltFusId ?? 2;
      logoteaser.userLogin = userLogins[index] ?? 'UNKNOWN';
    });

  }

  mediaLoad(): void {
    this.title = 'Medias';
  }

  ngOnInit(): void {
    this.title = 'Réferentiel';
    this.loadAll();
    this.storedSearchValue = localStorage.getItem("storedSearchValueLogo")? localStorage.getItem("storedSearchValueLogo") : "";
    if(this.storedSearchValue){
      this.myTab.filterGlobal(this.storedSearchValue, 'contains')
    }
  }

  onSearchChange(event: any){
    this.storedSearchValue = event.target.value;
    localStorage.setItem("storedSearchValueLogo", this.storedSearchValue.toString());
  
  }
  
  /*
  =======================================
  Commun à toutes les listes
  =======================================
  */

  delete(id: any): void {
   
    this.logoTeaserService.delete(id).pipe(first()).subscribe({
      next: response => {
        console.log(response)
        this.reloadPage()
        this.alertService.addMessage({ severity: AlertSeverity.SUCCESS, summary: 'Suppression', detail: "Le référentiel a été supprimé." });
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
        this.reloadPage()
        this.alertService.addMessage({ severity: AlertSeverity.ERROR, summary: 'Suppression', detail: "Une erreur est survenue ! Le référentiel n'a pas pu être supprimé." });
      }
    })
    
  }

  openEditForm(id: number) {
    this.modal.open(ModalTypeEnum.EDIT,this.logoteasers.find((logoTeaser: LogoTeaser) => logoTeaser.id == id));
  }

  openCreateForm() {
    this.modal.open(ModalTypeEnum.CREATE);
 
  }

  reloadPage() {
    this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.route.navigate(['logo-teaser-list'])
    })
  }

  openConfirmationDialog(id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }
  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }

  first = Number(localStorage.getItem("pageLogoTeaser"));
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageLogoTeaser", this.first.toString());
}
}
