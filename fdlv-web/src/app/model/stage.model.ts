import { IEvent } from './event.model';
import { IQuizz } from './quizz.model';
import { FdlvVideo } from './webfdlv/fdlv-video.model';

export interface IStage {
  id?: number;
  label?: string;
  sequence?: number;
  latitude?: number;
  longitude?: number;
  videoUrl?: string | null;
  event?: IEvent | null;
  quizz?: IQuizz | null;
  videoquizz?: IVideoQuizz | null;
  typeMedia?: string;
  videoDescription?: string;
  videoImageUrl?: string;
  video?: FdlvVideo | null;
  stage_defi_video?: string;
  stage_defi_partage?: string;
}

export class Stage implements IStage {
  constructor(
    public id?: number,
    public label?: string,
    public sequence?: number,
    public latitude?: number,
    public longitude?: number,
    public videoUrl?: string | null,
    public event?: IEvent | null,
    public quizz?: IQuizz | null,
    public videoquizz?: IVideoQuizz | null,
    public typeMedia?: string,
    public videoDescription?: string,
    public videoImageUrl?: string,
    public video?: FdlvVideo | null,
    public stage_defi_video?: string,
    public stage_defi_partage?: string
  ) { }
}

export function getStageIdentifier(stage: IStage): number | undefined {
  return stage.id;
}

export interface IVideoQuizz {
  vqu_id?: number;
  vqu_flv_id?: number;
  vqu_quizz_id?: number;
}
