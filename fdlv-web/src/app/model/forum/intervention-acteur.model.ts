import {Acteur} from "./acteur.model";
import {Forum} from "./forum.model";
import {RoleActeur} from "./role-acteur.model";
import {Structure} from "./structure.model";

export interface InterventionActeur {
  finId?: number;
  acteur?: Acteur;
  forum?: Forum;
  roleActeur?: RoleActeur;
  structure?: Structure;
  finDescription?: string;
  finDateDebut?: Date;
  finDateFin?: Date;
  finCommentaire?: string;
  finCharteAssoSigne?: string;
  finCharteAssoSigneDate?: Date;
}
