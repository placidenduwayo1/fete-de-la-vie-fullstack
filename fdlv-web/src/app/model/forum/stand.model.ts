import { StandSecteur } from './stand-secteur.model'

export interface Stand {
  id?: number
  forumStandSecteur?: any // Changer le type en any
  standPhysique?: string
  libelle?: string
  reference?: string
  materiel?: string
  observation?: string
  besoinElectricite?: string
  commentaire?: string
}
