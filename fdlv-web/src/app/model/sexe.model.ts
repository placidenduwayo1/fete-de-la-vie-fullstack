export class Sexe {
  dbValue?: number;
  ihmName?: string;
  enumName?: string;
}

// Must match class Sexe.java in API project,
// enumName must match the names in API project, as it is used to map
// the values between frontend & backend
export const Sexes: Sexe[] = [
  { dbValue: 0, ihmName: 'Inconnu', enumName: 'UNKNOWN' },
  { dbValue: 1, ihmName: 'Masculin', enumName: 'MALE' },
  { dbValue: 2, ihmName: 'Féminin', enumName: 'FEMALE' },
];
