import { Routes } from '@angular/router'
import { HomeComponent } from './pages/home/home.component'
import { LoginComponent } from './pages/login/login.component'
import { FdlvUserListComponent } from './pages/admin/user-management/List/fdlv-user-list/fdlv-user-list.component'
import { SettingsComponent } from './pages/account/settings/settings.component'
import { LogoutComponent } from './pages/logout/logout.component'
import { RoleActeurComponent } from './pages/forum/referentiel/role-acteur/role-acteur.component'
import { RoleStructureComponent } from './pages/forum/referentiel/role-structure/role-structure.component'
import { ModulesForumsComponent } from './pages/forum/referentiel/modules-forums/modules-forums.component'
import { BanniereComponent } from './pages/forum/referentiel/banniere/banniere.component'
import { TypeDocumentComponent } from './pages/forum/referentiel/type-document/type-document.component'
import { ListeDesStandsComponent } from './pages/forum/referentiel/liste-des-stands/liste-des-stands.component'
import { SecteurStandComponent } from './pages/forum/referentiel/secteur-stand/secteur-stand.component'
import { TestimoniesComponent } from './pages/webfdlv/testimony-list/testimony-list.component'
import { YtbVideosComponent } from './pages/webfdlv/ytb-video-list/ytb-video-list.component'
import { FdlvPartenaireListComponent } from './pages/webfdlv/fdlv-partenaire-list/fdlv-partenaire-list.component'
import { FdlvInfoPageWebComponent } from './pages/webfdlv/fdlv-info-page-web-list/fdlv-info-page-web-list.component'
import { FdlvVideosComponent } from './pages/webfdlv/fdlv-video-list/fdlv-video-list.component'
import { ConstructComponent } from './pages/shared/construct/construct.component'
import { FdlvPartenaireCreateComponent } from './pages/webfdlv/create/fdlv-partenaire-create/fdlv-partenaire-create.component'
import { PasswordComponent } from './pages/account/password/password.component'
import { LogoTeaserListComponent } from './pages/list/logo-teaser-list/logo-teaser-list.component'
import { YtbVideoCreateComponent } from './pages/webfdlv/create/ytb-video-create/ytb-video-create.component'
import { FdlvInfoPageWebCreateComponent } from './pages/webfdlv/create/fdlv-info-page-web-create/fdlv-info-page-web-create.component'
import { FdlvVideoCreateComponent } from './pages/webfdlv/create/fdlv-video-create/fdlv-video-create.component'
import { TestimonyCreateComponent } from './pages/webfdlv/create/testimony-create/testimony/testimony-create.component'
import { ThemeListComponent } from './pages/list/theme-list/theme-list.component'
import { QuizzListComponent } from './pages/list/quizz-list/quizz-list.component'
import { AideComponent } from './pages/aide/aide.component'
import { MobileUserListComponent } from './pages/mobile/mobile-user-list/mobile-user-list.component'
import { MobileUserCreateComponent } from './pages/mobile/create/mobile-user-create/mobile-user-create.component'
import { MobileQfinParcoursListComponent } from './pages/mobile/mobile-qfin-parcours-list/mobile-qfin-parcours-list.component'
import { MobileQfinParcoursCreateComponent } from './pages/mobile/create/mobile-qfin-parcours-create/mobile-qfin-parcours-create.component'
import { MobileAvisParcoursListComponent } from './pages/mobile/mobile-avis-parcours-list/mobile-avis-parcours-list.component'
import { MobileAvisParcoursCreateComponent } from './pages/mobile/create/mobile-avis-parcours-create/mobile-avis-parcours-create.component'
import { FdlvUserWebListComponent } from './pages/webfdlv/fdlv-user-web-list/fdlv-user-web-list.component'
import { FdlvUserCreateComponent } from './pages/webfdlv/create/fdlv-user-create/fdlv-user-create.component'
import { StageListComponent } from './pages/list/step-list/step-list.component'
import { StepCreateComponent } from './pages/list/step-list/step-create/step-create.component'
import { QuizzViewComponent } from './pages/list/step-list/quizz-view/quizz-view.component'
import { EventViewComponent } from './pages/list/step-list/event-view/event-view.component'
import { EventListComponent } from './pages/list/event-list/event-list.component'
import { EventCreateComponent } from './pages/list/event-list/event-create/event-create.component'
import { ThemeModalComponent } from './pages/list/theme-list/theme-modal/theme-modal.component'
import { QuizzModalComponent } from './pages/list/quizz-list/quizz-modal/quizz-modal.component'
import { ActeurComponent } from './pages/forum/acteur/acteur/acteur.component'
import { ActeurFormComponent } from './pages/forum/acteur/acteur_form/acteur-form/acteur-form.component'
import { AjoutModifFicheForumComponent } from './pages/forum/fiche-forum/fiche-forum-ajout-modif/ajout-modif-fiche-forum.component'
import { FicheForumComponent } from './pages/forum/fiche-forum/fiche-forum.component'
import { ForumThemeComponent } from './pages/forum/referentiel/forum-theme/forum-theme.component'
import { AjoutModifBanniereComponent } from './pages/forum/referentiel/banniere/banniere-ajout-modif/ajout-modif-banniere.component'
import { StructureFormComponent } from './pages/forum/structure/structure-form/structure-form.component'
import { StructureComponent } from './pages/forum/structure/structure.component'
import { InterventionStructureComponent } from './pages/forum/structure/intervention-structure/intervention-structure.component'
import { InterventionStructurePrinterComponent } from './pages/forum/structure/intervention-structure/intervention-structure-printer/intervention-structure-printer.component'
import { InterventionStructureUpdateComponent } from './pages/forum/structure/intervention-structure/intervention-structure-update/intervention-structure-update.component'
import { InterventionStructureCreateComponent } from './pages/forum/structure/intervention-structure/intervention-structure-create/intervention-structure-create.component'
import { InterventionsForStructureComponent } from './pages/forum/structure/interventions-for-structure/interventions-for-structure.component'
import { GetInterventionForStructureResolve } from './services/forum/intervention-structure.service'

export const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'theme-list',
    component: ThemeListComponent
  },
  {
    path: 'theme-create',
    component: ThemeModalComponent
  },
  {
    path: 'quizz-list',
    component: QuizzListComponent
  },
  {
    path: 'quizz-create',
    component: QuizzModalComponent
  },
  /*{
    path: 'event-list',
    component: TableComponent<Event>,
    data: {
      columnData: EventDetailColumnData,
      data: EventDetailMock
    }
  },*/
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'logout',
    component: LogoutComponent
  },
  {
    path: 'user-management',
    component: FdlvUserListComponent
  },
  {
    path: 'password',
    component: PasswordComponent
  },
  {
    path: 'forum/fiche-forum',
    component: FicheForumComponent
  },
  {
    path: 'fichesforum',
    component: FicheForumComponent,
  },
  {
    path: 'ficheforum-modif',
    component: AjoutModifFicheForumComponent,
  },
  {
    path: 'forum/referentiel/role-acteur',
    component: RoleActeurComponent
  },
  {
    path: 'forum/referentiel/role-structure',
    component: RoleStructureComponent
  },
  {
    path: 'forum/referentiel/secteur-stand',
    component: SecteurStandComponent
  },
  {
    path: 'forum/referentiel/liste-stands',
    component: ListeDesStandsComponent
  },
  {
    path: 'forum/referentiel/type-document',
    component: TypeDocumentComponent
  },
  {
    path: 'forum/referentiel/forum-theme',
    component: ForumThemeComponent
  },
  {
    path: 'forum/referentiel/banniere',
    component: BanniereComponent
  },
  {
    path: 'forum/referentiel/banniere-modif',
    component: AjoutModifBanniereComponent
  },
  {
    path: 'forum/referentiel/modules-forums',
    component: ModulesForumsComponent
  },

  {
    path: 'settings',
    component: SettingsComponent
  },
  {
    path: 'forum/acteurs',
    component: ActeurComponent
  },
  {
    path: 'forum/newacteur',
    component: ActeurFormComponent,
  },
  //testimony
  {
    path: 'testimony-list',
    component: TestimoniesComponent,
  }
  ,
  {
    path: 'testimony-create',
    component: TestimonyCreateComponent,
  },

  //ytb video
  {
    path: 'ytb-video-list',
    component: YtbVideosComponent,
  },
  {
    path: 'ytb-video-create',
    component: YtbVideoCreateComponent,
  },
  //partenaire
  {
    path: 'partenaires-list',
    component: FdlvPartenaireListComponent,
  },
  {
    path: 'fdlv-partenaire-create',
    component: FdlvPartenaireCreateComponent,
  },

  // infos web
  {
    path: 'infos-page-web-list',
    component: FdlvInfoPageWebComponent,
  },
  {
    path: 'info-page-web-create',
    component: FdlvInfoPageWebCreateComponent,
  },
  // videos 
  {
    path: "fdlv-video-list",
    component: FdlvVideosComponent
  }
  , {
    path: "fdlv-video-create",
    component: FdlvVideoCreateComponent
  },
  // videos 
  {
    path: "construct",
    component: ConstructComponent
  },
  {
    path: "logo-teaser-list",
    component: LogoTeaserListComponent
  },
  {
    path: "aide",
    component: AideComponent
  },
  {
    path: "structure",
    component: StructureComponent
  },
  {
    path: 'newstructure',
    component: StructureFormComponent,
  }
  , {
    path: "mobile-user-list",
    component: MobileUserListComponent
  }, {
    path: "mobile-user-create",
    component: MobileUserCreateComponent
  },
  {
    path: "mobile-qfin-list",
    component: MobileQfinParcoursListComponent
  },
  {
    path: "mobile-qfin-parcours-create",
    component: MobileQfinParcoursCreateComponent
  },
  {
    path: "mobile-avis-list",
    component: MobileAvisParcoursListComponent
  },
  {
    path: "mobile-avis-parcours-create",
    component: MobileAvisParcoursCreateComponent
  },
  {
    path: "fdlv-user-web-list",
    component: FdlvUserWebListComponent
  },
  {
    path: "fdlv-user-create",
    component: FdlvUserCreateComponent
  }, //étapes
  {
    path: "step-list",
    component: StageListComponent
  },
  {
    path: "step-create",
    component: StepCreateComponent
  },
  {
    path: 'quizz-view',
    component: QuizzViewComponent,
  },
  {
    path: 'event-view',
    component: EventViewComponent,
  },
  //event
  {
    path: "event-list",
    component: EventListComponent
  },
  {
    path: "event-create",
    component: EventCreateComponent
  },
  //new modif debut
  {
    path: 'intervention-structure-print',
    component: InterventionStructurePrinterComponent
  },
  {
    path: 'intervention-structure-create/:structureId',
    component: InterventionStructureCreateComponent
  },
  {
    path: 'intervention-structure-edit/:fsnId',
    component: InterventionStructureUpdateComponent
  },
  {
    path: 'interventions-structure-print/:structureId',
    component: InterventionsForStructureComponent,
    resolve:{
      getInterventionForStructureResolve: GetInterventionForStructureResolve
    }
  }
  //new modif fin
]
