import { Time } from '@angular/common';

export interface Forum {
  id?: number;
  numForum?: number;
  libelle?: string;
  reference?: string;
  typeForum?: string;
  sloganForum?: string;
  dateDebutEvenement?: Date;
  dateFinEvenement?: Date;
  dateDebutReservation?: Date;
  dateFinReservation?: Date;
  dateOuverture?: Date;
  heureOuverture?: String;
  dateFermeture?: Date;
  heureFermeture?: String;
  lieuForum?: string;
  adresseForum?: string;
  complementAdresseForum?: string;
  codePostal?: string;
  commune?: string;
  descriptionConvention?: string;
  conventionUrl?: string;
  isValid?: string;
  planForumUrl?: string;
  planSalle?: string;
  commentaire?: string;
}
