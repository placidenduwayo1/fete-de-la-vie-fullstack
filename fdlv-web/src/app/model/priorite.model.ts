export class Priorite {
  dbValue?: number;
  ihmName?: string;
  enumName?: string;
}

export const Priorites: Priorite[] = [
  { dbValue: 2, ihmName: 'Haut de la Page', enumName: 'HAUT_DE_LA_PAGE' },
  { dbValue: 1, ihmName: 'Bas de la Page', enumName: 'BAS_DE_LA_PAGE' },
];
