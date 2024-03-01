import {Structure} from './structure.model';
import {Acteur} from './acteur.model';
import {Forum} from './forum.model';

export interface Document {
  id?: number;
  numero?: number;
  libelle?: string;
  reference?: string;
  structure?: Structure;
  typeDocument?: DocumentType;
  forum?: Forum;
  acteur?: Acteur;
  extensionDocument?: string;
  urlDocument?: string;
  dateDocument?: Date;
  versionDocument?: string;
  niveauDiffusion?: string;
  docValide?: string;
  commentaire?: string;
}
