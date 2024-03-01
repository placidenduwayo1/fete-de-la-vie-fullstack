import {Forum} from "./forum.model";
import {Acteur} from "./acteur.model";
import {Structure} from "./structure.model";

export interface PlanningAction {
  id?: number;
  forum?: Forum;
  typeOrganisation?: string;
  reference?: string;
  acteur?: Acteur;
  structure?: Structure;
  action?: string;
  structureResponsable?: Structure;
  acteurResponsable?: Acteur;
  niveauDiffusion?: string;
  retroAction?: string;
  dateAction?: Date;
  heureDebut?: Date;
  heureFin?: Date;
  commentaire1?: string;
  commentaire2?: string;
  commentaire3?: string;
  isValid?: string;
}
