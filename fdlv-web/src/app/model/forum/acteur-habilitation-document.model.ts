import {RoleActeur} from "./role-acteur.model";
import {Document} from "./document.model";

export interface ActeurHabilitationDocument {
  id?: number;
  forumRoleActeur?: RoleActeur;
  document?: Document;
  habilitation?: string;
  commentaire?: string;
}
