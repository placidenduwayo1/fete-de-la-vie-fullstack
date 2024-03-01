export class Profil {
  dbValue?: number;
  ihmName?: string;
  enumName?: string;
}

// Must match class Profil.java in API project,
// enumName must match the names in API project, as it is used to map
// the values between frontend & backend
export const Profils: Profil[] = [
  { dbValue: 1, ihmName: 'Organisateur', enumName: 'ORGANISATEUR' },
  { dbValue: 2, ihmName: 'Professionnel', enumName: 'PROFESSIONNEL' },
  { dbValue: 3, ihmName: 'Bénévole', enumName: 'BENEVOLE' },
  { dbValue: 4, ihmName: 'Public', enumName: 'PUBLIC' },
  { dbValue: 9, ihmName: 'Administrateur Organisateur', enumName: 'ADMINISTRATEUR' },
];
