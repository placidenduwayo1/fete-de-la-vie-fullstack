import * as dayjs from 'dayjs';

export interface IFdlvChoixOrga {
  fco_id?: number;
  fco_fus_id?: number;
  fco_num_scenario?: number;
  fco_video_id?: number;
  fco_num_ordre?: number;
}

export class FdlvChoixOrga implements IFdlvChoixOrga {
  constructor(
    public fco_id?: number,
    public fco_fus_id?: number,
    public fco_num_scenario?: number,
    public fco_video_id?: number,
    public fco_num_ordre?: number
  ) {}
}
