import * as dayjs from 'dayjs';

export interface IYtbVideo {
  id?: null | number;
  ordreAffichage?: null | number;
  urlVideo?: string;
  dateDebut?: dayjs.Dayjs | null;
  dateFin?: dayjs.Dayjs | null;
  publie?: boolean;
  commentaire?: string;
}

export class YtbVideo implements IYtbVideo {
  constructor(
    public id?: number ,
    public ordreAffichage?: number,
    public urlVideo?: string,
    public dateDebut?: dayjs.Dayjs | null,
    public dateFin?: dayjs.Dayjs| null,
    public publie?: boolean,
    public commentaire?: string
  ) {}
}

export function getYtbVideoIdentifier(ytbVideo: IYtbVideo): number | undefined {
  return ytbVideo.id;
}
