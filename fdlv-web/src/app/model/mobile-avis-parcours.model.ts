import * as dayjs from 'dayjs';

export class MobileAvisParcours {
  id?: number;
  questionFinParcoursId?: number;
  mobileUserId?: number;
  eventId?: number;
  question?: string;
  avis?: string;
  date?: dayjs.Dayjs|string;
}
