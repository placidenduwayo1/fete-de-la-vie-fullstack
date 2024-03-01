import {Acteur} from "./acteur.model";
import {ModuleApplicatif} from "./module-applicatif.model";

export interface ActeurHabilitationModule {
  id?: number;
  acteur?: Acteur;
  moduleApplicatif?: ModuleApplicatif;
  habilitation?: string;
  commentaire?: string;
}
