import { Component } from '@angular/core'
import { CommonModule } from '@angular/common'
import { ButtonComponent } from '../button/button.component'
import { MenubarModule } from 'primeng/menubar'
import { MenuItem } from 'primeng/api'
import packageInfos from 'package.json'

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, MenubarModule, ButtonComponent],
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  public version = packageInfos.version
  public items: MenuItem[] = [
    {
      label: 'Accueil',
      icon: 'pi pi-fw pi-home',
      routerLink: 'home',
      styleClass: 'light'
    },
    {
      label: 'GoMyDefi',
      icon: 'pi pi-fw pi-server',
      items: [
        {
          label: 'Thèmes',
          icon: 'pi pi-fw pi-save',
          routerLink: 'theme-list'
        },
        {
          label: 'Parcours',
          icon: 'pi pi-fw pi-star',
          routerLink: 'event-list'
        },
        {
          label: 'Étapes',
          icon: 'pi pi-fw pi-flag',
          routerLink: 'step-list'
        },
        {
          label: 'Quizz',
          icon: 'pi pi-fw pi-question-circle',
          routerLink: 'quizz-list'
        },
        {
          label: 'Référentiel',
          icon: 'pi pi-fw pi-eye',
          routerLink: 'logo-teaser-list'
        },
        {
          label: 'Aide',
          icon: 'pi pi-fw pi-info',
          routerLink: 'aide'
        }
      ],
      styleClass: 'light'
    },
    {
      label: 'Mobile',
      icon: 'pi pi-fw pi-mobile',
      items: [
        {
          label: 'Utilisateur',
          icon: 'pi pi-fw pi-users',
          routerLink: 'mobile-user-list'
        },
        {
          label: 'Question Fin Parcours',
          icon: 'pi pi-fw pi-question-circle',
          routerLink: 'mobile-qfin-list'
        },
        {
          label: 'Avis Fin Parcours',
          icon: 'pi pi-fw pi-comments',
          routerLink: 'mobile-avis-list'
        },
        {
          label: 'Aide',
          icon: 'pi pi-fw pi-info',
          routerLink: 'construct'
        }
      ],
      styleClass: 'light'
    },
    {
      label: 'Web FDLV',
      icon: 'pi pi-fw pi-globe',
      items: [
        {
          label: 'Derniers Evts',
          icon: 'pi pi-fw pi-calendar',
          routerLink: 'construct'
        },
        {
          label: 'Partenaires',
          icon: 'pi pi-fw pi-user-plus',
          routerLink: 'partenaires-list'
        },
        {
          label: 'Témoignages',
          icon: 'pi pi-fw pi-comments',
          routerLink: 'testimony-list'
        },
        {
          label: 'Actions en vidéo',
          icon: 'pi pi-fw pi-video',
          routerLink: 'ytb-video-list'
        },
        {
          label: 'Infos FDLV',
          icon: 'pi pi-fw pi-verified',
          routerLink: 'infos-page-web-list'
        },
        {
          label: 'Médias organisateur',
          icon: 'pi pi-fw pi-video',
          routerLink: 'fdlv-video-list'
        },
        {
          label: 'Contact',
          icon: 'pi pi-fw pi-envelope',
          routerLink: 'construct'
        },
        {
          label: 'Utilisateur',
          icon: 'pi pi-fw pi-users',
          routerLink: 'fdlv-user-web-list'
        },
        {
          label: 'Aide',
          icon: 'pi pi-fw pi-info',
          routerLink: 'construct'
        }
      ],
      styleClass: 'light'
    },
    {
      label: 'Forum',
      icon: 'pi pi-fw pi-map-marker',
      items: [
        {
          label: 'Acteurs',
          icon: 'pi pi-fw pi-home',
          // routerLink: '/forum/acteurs',
          routerLink: 'forum/acteurs'
        },
        {
          label: 'Fiche Forum',
          icon: 'pi pi-fw pi-server',
          routerLink: 'forum/fiche-forum'
        },
        {
          label: 'Structure',
          icon: 'pi pi-fw pi-book',
          routerLink: 'structure'
        },
        {
          label: 'Documents',
          icon: 'pi pi-fw pi-star',
          routerLink: '/forum/documents'
        },
        {
          label: 'Planning',
          icon: 'pi pi-fw pi-calendar',
          routerLink: '/forum/planning'
        },
        {
          label: 'Habilitation',
          icon: 'pi pi-fw pi-question-circle',
          items: [
            {
              label: 'Acteur / Module',
              icon: 'pi pi-fw pi-map',
              routerLink: '/forum/habilitation/acteur-module'
            },
            {
              label: 'Acteur / Document',
              icon: 'pi pi-fw pi-eye',
              routerLink: '/forum/habilitation/acteur-document'
            },
            {
              label: 'Structure / Document',
              icon: 'pi pi-fw pi-info',
              routerLink: '/forum/habilitation/structure-document'
            }
          ]
        },
        {
          label: 'Référentiel',
          icon: 'pi pi-fw pi-mobile',
          items: [
            {
              label: 'Rôle Acteur',
              icon: 'pi pi-fw pi-users',
              routerLink: '/forum/referentiel/role-acteur'
            },
            {
              label: 'Rôle Structure',
              icon: 'pi pi-fw pi-database',
              routerLink: '/forum/referentiel/role-structure'
            },
            {
              label: 'Secteur Stand',
              icon: 'pi pi-fw pi-desktop',
              routerLink: '/forum/referentiel/secteur-stand'
            },
            {
              label: 'Liste des Stands',
              icon: 'pi pi-fw pi-list',
              routerLink: '/forum/referentiel/liste-stands'
            },
            {
              label: 'Type Document',
              icon: 'pi pi-fw pi-comments',
              routerLink: '/forum/referentiel/type-document'
            },
            {
              label: 'Thème Forum',
              icon: 'pi pi-fw pi-video',
              routerLink: '/forum/referentiel/forum-theme'
            },
            {
              label: 'Bannière',
              icon: 'pi pi-fw pi-video',
              routerLink: '/forum/referentiel/banniere'
            },
            {
              label: 'Modules Forums',
              icon: 'pi pi-fw pi-flag',
              routerLink: '/forum/referentiel/modules-forums'
            }
          ]
        }
      ],
      styleClass: 'light'
    },
    {
      label: 'Administration',
      icon: 'pi pi-fw pi-cog',
      items: [
        {
          label: 'Utilisateurs',
          icon: 'pi pi-fw pi-users',
          routerLink: 'user-management'
        },
        {
          label: 'Métriques',
          icon: 'pi pi-fw pi-chart-line',
          routerLink: 'metrics'
        },
        {
          label: 'Santé',
          icon: 'pi pi-fw pi-heart',
          routerLink: 'health'
        },
        {
          label: 'Configuration',
          icon: 'pi pi-fw pi-sliders-h',
          routerLink: 'configuration'
        },
        {
          label: 'Logs',
          icon: 'pi pi-fw pi-exclamation-triangle',
          routerLink: 'logs'
        }
      ],
      styleClass: 'light'
    },
    {
      label: 'Mon compte',
      icon: 'pi pi-fw pi-user',
      items: [
        {
          label: 'Paramètres',
          icon: 'pi pi-fw pi-wrench',
          routerLink: 'settings'
        },
        {
          label: 'Mot de passe',
          icon: 'pi pi-fw pi-lock',
          routerLink: 'password'
        },
        {
          label: 'Déconnexion',
          icon: 'pi pi-fw pi-sign-out',
          routerLink: 'logout'
        }
      ],
      styleClass: 'light'
    }
  ]
}
