import {Structure} from "./structure.model";
import {Forum} from "./forum.model";
import {RoleStructure} from "./role-structure.model";
import { Stand } from "./stand.model";
import { Banniere } from "./banniere.model";

export class InterventionStructure {
  fsnId: number;
  forum: Forum;
  structure: Structure;
  roleStructure: RoleStructure;
  //new modif debut
  stand?: Stand;
  banniere?: Banniere;
  //new modif fin
  fsnDescription?: string;
  fsnConventionSigne?: string;
  fsnDateConventionSigne?: Date;
  fsnDateRelance01?: Date;
  fsnDateRelance02?: Date;
  fsnDateRelance03?: Date;
  fsnDateRelance04?: Date;
  fsnDateRelance05?: Date;
  fsnCommentaire?: string;
}
