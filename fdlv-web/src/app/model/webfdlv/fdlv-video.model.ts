import { Quizz } from '../quizz.model';
import { ITheme } from '../theme.model';

export class FdlvVideo {
  id?: number;
  theme?: ITheme;
  numOrdre?: number;
  urlImage?: string;
  urlVideo?: string;
  description?: string;
  active?: boolean;
  nomVideo?: string;
  typeMedia?: string;
  flvFusId?: number;
  flvMediaValide?: string;
  userLogin?: string;
  quizzs?: Quizz[];
}

// TODO: best practice implement service which retrieve possible values from database with enum
export function getTypeOfMedias(): string[] {
  return ['.mp4', '.mp3', '.png', '.jpg', '.jpeg', '.pdf'];
}
