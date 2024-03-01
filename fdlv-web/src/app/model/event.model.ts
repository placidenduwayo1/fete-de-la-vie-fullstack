import * as dayjs from 'dayjs';
import { ITheme } from './theme.model';
import { IStage } from './stage.model';

export interface IEvent {
  id?: number;
  label?: string;
  description?: string;
  address?: string;
  zipCode?: string;
  city?: string;
  cityLogoUrl?: string | null;
  startAt?: dayjs.Dayjs |string | any ;
  endAt?: dayjs.Dayjs |string | any;
  labelFinParcours?: string | null;
  finParcoursPdf?: string | null;
  theme?: ITheme | null;
  stages?: IStage[] | null;
  otherEvent?: boolean;
  validatedEvent?: boolean;
  fixOrder?: boolean;
  usefulInformation?: string | null;
  eventTeaserUrl?: string | null;
  codeParcours?: string | null;
  evtDemo?: boolean | null;
  numParcours?: number | null;
  evtFcoFusId?: number | null;
  evtFcoDatePropose?: dayjs.Dayjs | string | null;
  evtFcoDateValide?: dayjs.Dayjs | string | null;
  evtFcoId?: number | null;
}

export class Event implements IEvent {
  constructor(
    public id?: number,
    public label?: string,
    public description?: string,
    public address?: string,
    public zipCode?: string,
    public city?: string,
    public cityLogoUrl?: string | null,
    public startAt?: dayjs.Dayjs |string,
    public endAt?: dayjs.Dayjs |string ,
    public labelFinParcours?: string | null,
    public finParcoursPdf?: string | null,
    public theme?: ITheme | null,
    public stages?: IStage[] | null,
    public otherEvent?: boolean,
    public validatedEvent?: boolean,
    public fixOrder?: boolean,
    public usefulInformation?: string | null,
    public eventTeaserUrl?: string | null,
    public codeParcours?: string | null,
    public evtDemo?: boolean | null,
    public numParcours?: number | null,
    public evtFcoFusId?: number | null,
    public evtFcoDatePropose?: dayjs.Dayjs | string | null,
    public evtFcoDateValide?: dayjs.Dayjs | string | null,
    public evtFcoId?: number | null
  ) {}
}

export function getEventIdentifier(event: IEvent): number | undefined {
  return event.id;
}
