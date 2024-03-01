export class ModulePartenaire {
  dbValue?: number;
  ihmName?: string;
  enumName?: string;
}

export const ModulePartenaires: ModulePartenaire[] = [
  { dbValue: 1, ihmName: 'Site Web', enumName: 'WEB' },
  { dbValue: 2, ihmName: 'App Mobile', enumName: 'MOBILE' },
  { dbValue: 3, ihmName: 'Site Web et App Mobile', enumName: 'WEB_MOBILE' },
  { dbValue: 0, ihmName: 'Non affiché"', enumName: 'NON_AFFICHE' },
];
