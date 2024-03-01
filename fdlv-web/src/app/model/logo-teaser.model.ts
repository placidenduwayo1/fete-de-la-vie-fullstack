export class LogoTeaser {
  id?: number;
  zipCode?: string;
  city?: string;
  typeMedia?: string;
  label?: string;
  url?: string;
  rltFusId?: number;
  rltLogoTeaserValide?: string;
  userLogin?: string;
  dateDebut: any;
  dateFin: any;
}
export function getlogoteaserIdentifier(logoteaser: LogoTeaser): number | undefined {
  return logoteaser.id;
}
