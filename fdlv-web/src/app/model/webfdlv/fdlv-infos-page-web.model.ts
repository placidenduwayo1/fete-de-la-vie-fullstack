import * as dayjs from 'dayjs';

export class InfosPageWeb {
  id!: number;
  pageWeb!: string;
  rubriquePageWeb?:string;
  ordreAffichage!: number;
  titre!: string;
  texte!: string;
  commentaire!: string;
  publie!: boolean;
  urlMedia!: string | ArrayBuffer;
  dateDebut?: dayjs.Dayjs;
  dateFin?: dayjs.Dayjs;
}
