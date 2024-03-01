import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PanelModule } from 'primeng/panel';
import { ToastModule } from 'primeng/toast';
import { Table } from 'primeng/table';
import { MessageService } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { FormsModule } from '@angular/forms';
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
import { Stand } from 'src/app/model/forum/stand.model';
import { StandService } from 'src/app/services/forum/stand.service';
import { StandSecteur } from 'src/app/model/forum/stand-secteur.model';
import { StandSecteurService } from 'src/app/services/forum/stand-secteur.service';
import { DropdownModule } from 'primeng/dropdown';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { SearchServiceService } from 'src/app/services/forum/search-service.service';
import { concatMap } from 'rxjs';

@Component({
  selector: 'app-liste-des-stands',
  standalone: true,
  imports: [
    ToggleButtonModule,
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
  templateUrl: './liste-des-stands.component.html',
  styleUrls: ['./liste-des-stands.component.scss'],
})
export class ListeDesStandsComponent implements OnInit {
  @ViewChild('myTab', { static: true }) myTab: Table;
  stands: Stand[] = [];
  standSecteur: StandSecteur[] = [];
  data: any = [];
  displayAddForm = false;
  displayEditForm: boolean = false;
  datauser: Stand = { standPhysique: 'Non'};
  errormessage: boolean = false;
  showConfirmationDialog: boolean = false;
  idToDelete: number = 0;
  standSecteurOptions: any[] = [];
  selectedStandSecteurId: number | null = null;
  selectedStandSecteur: any | null = null; // Pour stocker l'objet complet du secteur sélectionné
  currentPage: number;
  searchValue: string = '';

  constructor(
    private searchService: SearchServiceService,
    private standService: StandService,
    private route: Router,
    private standSecteurService: StandSecteurService,
    private messageService: MessageService
  ) {
    this.displayEditForm = false;
  }

  ngOnInit() {
    this.standService.getAllStands().subscribe((response: Stand[]) => {
      this.stands = response.map((stand: Stand) => {
        // If forumStandSecteur exists, use its libelle property, otherwise use an empty string
        const forumStandSecteurLibelle = stand.forumStandSecteur
          ? stand.forumStandSecteur.libelle
          : '';

        // Create a new object with the combined information
        return {
          ...stand,
          forumStandSecteurLibelle,
        };
      });
      // Récupérer la valeur de recherche au chargement de la page
      this.searchValue = this.searchService.getSearchValue();

      // Appliquer le filtre global avec la valeur récupérée
      this.myTab.filterGlobal(this.searchValue, 'contains');
    });
    this.standSecteurService
      .getAllStandSecteurs()
      .subscribe((response: any = []) => {
        this.standSecteur = response;

        // Remplissez le tableau standSecteurOptions avec les données de votre API
        this.standSecteurOptions = response.map((secteur: any) => ({
          label: `${secteur.libelle} (ID: ${secteur.id})`, // Afficher libellé et ID
          value: secteur, // Utilisez l'objet complet comme valeur
        }));
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

  getStatusLabel(statut: any): string {
    if (statut === false) {
      return 'Désactiver';
    } else {
      return 'Activer';
    }
  }

  openAddForm() {
    this.errormessage = false;
    this.datauser = { standPhysique: 'Non' } as Stand;
    this.displayAddForm = true;
  }

  closeAddForm() {
    this.displayAddForm = false;
  }

  openEditForm(stand: Stand) {
    this.errormessage = false;
    // Initialisez les valeurs de datauser à partir de l'objet Stand reçu
    this.datauser = stand;
    this.datauser.standPhysique =
      stand.standPhysique && stand.standPhysique.toUpperCase() === 'Oui'
        ? 'Oui'
        : 'Non';
    // Initialisez selectedStandSecteurId avec l'identifiant du secteur sélectionné
    this.selectedStandSecteurId = this.datauser.forumStandSecteur?.id || null;

    // Modification de la référence
    if (this.selectedStandSecteurId !== null) {
      const standSecteurCode = this.selectedStandSecteurId.toString();
      const standPhysiqueValue = this.datauser.standPhysique;
      const libelleFirstFiveLetters = this.datauser.libelle.slice(0, 5);
      this.datauser.reference = `${this.datauser.id}_${standSecteurCode}_${standPhysiqueValue}_${libelleFirstFiveLetters}`;
    }
    // Affichez le formulaire d'édition
    this.displayEditForm = true;
  }

  closeEditForm() {
    this.displayEditForm = false;
  }

  saveForm(formData: any) {
    formData.id = null;
 // Appelez le service de création du stand avec les données du formulaire
    this.standService
      .createStand(formData)
      .pipe(
        concatMap((response) => {
          if (
            formData.forumStandSecteur &&
            formData.standPhysique &&
            response &&
            response.id
          ) {
            const standSecteurCode = formData.forumStandSecteur.id.toString();
            const standPhysiqueValue = formData.standPhysique;
            const libelleFirstFiveLetters = formData.standPhysique.slice(0, 5);
            formData.reference = `${response.id}_${standSecteurCode}_${standPhysiqueValue}_${libelleFirstFiveLetters}`;
            formData.id = response.id;
            return this.standService.updateStand(response.id, formData);
          } else {
            this.messageService.add({
              severity: 'error',
              summary: 'Erreur lors de la modification.',
              detail: 'Données invalides : certaines données requises sont manquantes ou invalides.'
            });
            throw new Error(
              'Données invalides : certaines données requises sont manquantes ou invalides.'
            );
          }
        })
      )
      .subscribe(
        (updatedResponse) => {
          this.messageService.add({
            severity: 'success',
            summary: 'Création d\'un stand',
            detail: 'Le stand a été crée.'
          });
          this.closeAddForm();
        },
        (error: HttpErrorResponse) => {
          this.messageService.add({
            severity: 'error',
            summary: 'Erreur lors de la création du stand.',
            detail: error.error.title
          });
        }
      );
  }
  EditForm(stand: any) {
    // Appel du service pour mettre à jour le stand avec les données de datauser
    this.standService.updateStand(stand.id, stand).subscribe(
      (response) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Modification d\'un stand',
          detail: 'Le stand ' + stand.name + ' a été modifié.'
        });
        this.closeEditForm();
        this.ngOnInit();
      },
      (error: HttpErrorResponse) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur lors de la modification.',
          detail: error.error.title
        });
      }
    )
  };

  deleteStand() {
    this.standService.deleteStand(this.idToDelete).subscribe(
      (response) => {
        this.idToDelete = null;
        this.closeConfirmationDialog();
        this.messageService.add({
          severity: 'success',
          summary: 'Validé',
          detail: 'Suppression validée!',
        });
        this.ngOnInit();
      },
      (error: HttpErrorResponse) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Erreur lors de la suppression',
          detail: error.error.title
        });
      }
  )};

  openConfirmationDialog(id: number) {
    this.errormessage = false;
    this.idToDelete = id;
    this.showConfirmationDialog = true;
  }

  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }

  reloadPage() {
    const lastModifiedPage = localStorage.getItem('lastModifiedPage');
    const currentUrl = lastModifiedPage || this.route.url;
    this.route.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.route.navigate([currentUrl]);
    });
  }
  
  first = Number(localStorage.getItem("pageListeStand"))  ;
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageListeStand", this.first.toString());
}
}
