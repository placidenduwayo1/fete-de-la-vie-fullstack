import * as dayjs from 'dayjs';

export interface ITestimony {
  id?: number;
  auteur?: string;
  compagnie?: string;
  domaineMetier?: string;
  lienImageAuteur?: string | ArrayBuffer | null;
  ordreAffichage?: number;
  texteTemoignage?: string;
  publie?: boolean;
  dateDebut?: dayjs.Dayjs;
  dateFin?: dayjs.Dayjs;
  commentaire?: string;
}

export class Testimony implements ITestimony {
  constructor(
    public id?: number,
    public auteur?: string,
    public compagnie?: string,
    public domaineMetier?: string,
    public lienImageAuteur?: string | ArrayBuffer | null,
    public ordreAffichage?: number,
    public texteTemoignage?: string,
    public publie?: boolean,
    public dateDebut?: dayjs.Dayjs,
    public dateFin?: dayjs.Dayjs,
    public commentaire?: string,
  ) {}
}

export function getTestimonyIdentifier(theme: Testimony): number | undefined {
  return theme.id;
}
