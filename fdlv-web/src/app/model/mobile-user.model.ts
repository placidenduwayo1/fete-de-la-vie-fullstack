import { Sexe } from './sexe.model';
import { TrancheAge } from './tranche.age.model';
import * as dayjs from 'dayjs';

export class MobileUser {
  id?: number;
  pseudo?: string;
  sexe?: Sexe;
  trancheAge?: TrancheAge;
  dateFermeture?: dayjs.Dayjs;
}
