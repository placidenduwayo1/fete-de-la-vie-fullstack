import { Profil } from './profil.model';
import * as dayjs from 'dayjs';

export class FdlvUsers {
  id?: number;
  profil?: Profil;
  login?: string;
  mdpHash?: string;
  prenom?: string | ArrayBuffer;
  nom?: number;
  structure?: string;
  service?: string;
  email?: string;
  numTel?: string;
  actif?: boolean;
  dateDebut?: dayjs.Dayjs | null;
  dateFin?: dayjs.Dayjs | null;
  permanent?: boolean;
  resetKey?: string;
  creePar?: string;
  dateCreation?: dayjs.Dayjs;
  dateReset?: dayjs.Dayjs;
  modifiePar?: string;
  dateModif?: dayjs.Dayjs;
  // Propriété spécifique du DTO liée à gmd-admin qui permet de distinguer les appels entre les 2 sites
  fromFDLV?: boolean;
}
