import {Acteur} from "./acteur.model";
import {RoleActeur} from "./role-acteur.model";
import {Forum} from "./forum.model";
import {Stand} from "./stand.model";
import {Structure} from "./structure.model";
import {RoleStructure} from "./role-structure.model";
import {StandBanniere} from "./stand-banniere.model";

export interface InterventionStand {
  fsiId?: number;
  acteur?: Acteur;
  roleActeur?: RoleActeur;
  forum?: Forum;
  stand?: Stand;
  structure?: Structure;
  roleStructure?: RoleStructure;
  standBanniere?: StandBanniere;
  fsiDescription?: string;
  fsiDateDebut?: Date;
  fsiDateFin?: Date;
  fsiNbRepas?: number;
  fsiCommentaire?: string;
}
