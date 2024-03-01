import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PanelModule } from 'primeng/panel';
import { TableModule } from 'primeng/table';
import { FormsModule } from '@angular/forms';
import { Table } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { InputTextModule } from 'primeng/inputtext';
import { NavbarComponent } from 'src/app/ui/navbar/navbar.component';
import { Router, RouterOutlet } from '@angular/router';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { RippleModule } from 'primeng/ripple';
import { DialogModule } from 'primeng/dialog';
import { CheckboxModule } from 'primeng/checkbox';
import { MessageModule } from 'primeng/message';
import { HttpErrorResponse } from '@angular/common/http';
import { StandSecteur } from 'src/app/model/forum/stand-secteur.model';
import { StandSecteurService } from 'src/app/services/forum/stand-secteur.service';
import { SearchServiceService } from 'src/app/services/forum/search-service.service';
import { MessageService } from 'primeng/api';
@Component({
  selector: 'app-secteur-stand',
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
  templateUrl: './secteur-stand.component.html',
  styleUrls: ['./secteur-stand.component.scss'],
})
export class SecteurStandComponent implements OnInit {
  @ViewChild('myTab', { static: true }) myTab: Table;
  standSecteur: StandSecteur[] = [];
  confirmationMessage: string = '';
  selectedStandSecteur: StandSecteur | null = null;
  data: any = [];
  displayAddForm = false;
  displayEditForm = false;
  dataStandSecteur : StandSecteur = {}
  confirmationHeader: string;
  title: any;
  errormessage: boolean = false;
  showConfirmationDialog: boolean = false;
  searchValue: string = '';
  constructor(
    private searchService: SearchServiceService,
    private standSecteurService: StandSecteurService,
    private messageService : MessageService,
    private route: Router
  ) {}

  ngOnInit() {
    this.standSecteurService
      .getAllStandSecteurs()
      .subscribe((response: any = []) => {
        this.data = response;
        this.standSecteur = response;
        // Récupérer la valeur de recherche au chargement de la page
        this.searchValue = this.searchService.getSearchValue();

        // Appliquer le filtre global avec la valeur récupérée
        this.myTab.filterGlobal(this.searchValue, 'contains');
      });
  }

  onSearchInputChange(event: any): void {
    const newValue = event.target.value;
    this.searchValue = newValue;

    // Sauvegarder la nouvelle valeur de recherche
    this.searchService.saveSearchValue(newValue);

    // Appliquer le filtre global avec la nouvelle valeur
    this.myTab.filterGlobal(newValue, 'contains');
  }

  openAddForm() {
    this.errormessage = false;
    this.displayAddForm = true;
  }

  closeAddForm() {
    this.displayAddForm = false;
  }

  openEditForm(data: StandSecteur) {
    this.errormessage = false;
    this.dataStandSecteur = data
    this.displayEditForm = true;
    console.log(this.dataStandSecteur);
  }

  closeEditForm() {
    this.displayEditForm = false;
  }

  saveForm(standSecteur: StandSecteur) {
    if(!this.validateForm(standSecteur)){
      return;
    }
    this.standSecteurService.createStandSecteur(standSecteur).subscribe({
      next: (response) => {
        const message = {
          severity: 'success',
          summary: 'Création d\'un secteur stand',
          detail: 'Le secteur stand ' + standSecteur.codeSecteur + ' a été créé.',
        }
        this.messageService.add(message);
        this.closeAddForm();
        this.ngOnInit();
      },
      error: (error: HttpErrorResponse) => {
        this.errormessage = true;
        const message = {
          severity: 'error',
          summary: 'Création d\'un secteur stand',
          detail: 'Erreur lors de la création d\'un secteur stand: ' + error.error.title
        }
        this.messageService.add(message);
        this.closeAddForm();
        this.ngOnInit();
      },
    });
  }

  editForm(standSecteur: any) {
    if(!this.validateForm(standSecteur)){
      return;
    }
    this.standSecteurService
      .updateStandSecteur(this.dataStandSecteur.id, standSecteur)
      .subscribe(
        (response) => {
          const message = {
            severity: 'success',
            summary: 'Modification d\'un secteur stand',
            detail: 'Ce secteur stand a été modifié.',
          }
          this.messageService.add(message);
          this.closeEditForm();
          this.ngOnInit();
        },
        (error: HttpErrorResponse) => {
          this.errormessage = true;
          const message = {
            severity: 'error',
            summary: 'Création d\'un secteur stand',
            detail: 'Erreur lors de la création d\'un secteur stand'
          }
          this.messageService.add(message);
          this.closeEditForm();
          this.ngOnInit();
        }
      );
  }

  validateForm(stand: any) : boolean {
    if(stand.codeSecteur == "" || stand.codeSecteur.length > 3){
      this.title = "Le code stand est obligatoire et contient moins de 4 caractères."
      this.errormessage = true;
      return false;
    }
    if(stand.libelle == ""){
      this.title = "Le libelle du stand est obligatoire."
      this.errormessage = true;
      return false;
    } 
    return true;
  }

  confirmDeletion() {
      this.standSecteurService.deleteStandSecteur(this.selectedStandSecteur.id).subscribe(() => {
        const message = {
          severity: 'success',
          summary: 'Suppresion d\'un secteur stand',
          detail: 'Le secteur stand ' + this.selectedStandSecteur.codeSecteur + ' a été supprimé.',
        }
        this.messageService.add(message);
        this.closeConfirmationDialog();
        this.ngOnInit();
      });
  }

  openConfirmationDialog(secteur: StandSecteur) {
    // Vérifiez si la liste des stands n'est pas vide
    if (secteur.nbStands > 0) {
      this.messageService.add({
        severity: 'error',
        summary: 'Impossible de supprimer',
        detail: 'La suppression de ce secteur n\'est pas possible car il existe encore des Stands rattachés. Veuilez les supprimer en premier.',
      });
      return;
    }
    this.errormessage = false;
    this.selectedStandSecteur = secteur;
    this.showConfirmationDialog = true;
  }

  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
    this.selectedStandSecteur = null;
  }

  reloadPage() {
    this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.route.navigate(['forum/referentiel/secteur-stand']);
    });
  }
  
  first = Number(localStorage.getItem("pageSecteurStand"))  ;
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageSecteurStand", this.first.toString());
}
}
