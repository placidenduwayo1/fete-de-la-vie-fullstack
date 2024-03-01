import {Acteur} from './acteur.model';
import { NiveauResponsabilite } from './niveau-responsabilite.model';

export class Structure {
  id: number;
  code: string;
  libelle: string;
  reference: string;
  niveauResponsabilite: NiveauResponsabilite | any;
  logoDescription: string;
  logoUrl: string;
  charteDescription: string;
  charteUrl: string;
  adresse01: string;
  adresse02: string;
  cp: string;
  commune: string;
  contact: Acteur;
  telAccueilStructure: string;
  emailAccueilStructure: string;
  commentaire: string;
  conventionDescription: string;
  conventionUrl: string;
}
