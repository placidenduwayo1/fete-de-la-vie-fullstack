import { Structure } from './structure.model';
import { RoleActeur } from './role-acteur.model';
import { Forum } from './forum.model';
import * as dayjs from 'dayjs';
import { InterventionActeur } from './intervention-acteur.model';

export class Acteur {
  id?: number;
  reference?: string;
  name?: string;
  lastName?: string;
  login?: string;
  passwordHash?: string;
  photoUrl?: string;
  numTelPortable?: string;
  numTelBureau?: string;
  email?: string;
  createdBy?:string;
  acteurFDLV?: string;
  responsable?: string;
  creerUsers?: string;
  benevole?: string;
  correspondant?: string;
  service?: string;
  commentaire?: string;
  dateDebutValidite?: Date;
  dateFinValidite?: Date;
  actif?: string;
  presenceStand?: string;
  forum?: Forum;
  forumRoleActeur?: RoleActeur;
  structureId?: Structure;
  createdByActor?: Acteur;
  responsableActor?: Acteur;
  interventionActeurs?: InterventionActeur [];
}
