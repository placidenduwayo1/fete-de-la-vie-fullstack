export class TrancheAge {
  dbValue?: number;
  ihmName?: string;
  enumName?: string;
}

// Must match class TrancheAge.java in API project,
// enumName must match the names in API project, as it is used to map
// the values between frontend & backend
export const TrancheAges: TrancheAge[] = [
  { dbValue: 0, ihmName: 'Inconnue', enumName: 'UNKNOWN' },
  { dbValue: 1, ihmName: '10 ans et moins', enumName: 'LESS_THAN_10' },
  { dbValue: 2, ihmName: 'De 11 à 14 ans', enumName: 'FROM_11_TO_14' },
  { dbValue: 3, ihmName: 'De 15 à 17 ans', enumName: 'FROM_15_TO_17' },
  { dbValue: 4, ihmName: 'De 18 à 25 ans', enumName: 'FROM_18_TO_25' },
  { dbValue: 5, ihmName: 'De 26 à 49 ans', enumName: 'FROM_26_TO_49' },
  { dbValue: 6, ihmName: '50 ans et plus', enumName: 'MORE_THAN_50' },
];
