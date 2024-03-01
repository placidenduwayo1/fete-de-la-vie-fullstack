import { ModulePartenaire } from './partenaire-module.model';
import { Priorite } from './priorite.model';

export interface IPartenaire {
  id?: number;
  num_ordre?: any;
  image?: string ;
  priorite?: Priorite;
  module?: ModulePartenaire;
}

export class Partenaire implements IPartenaire {
  constructor(
    public id?: number,
    public num_ordre?: string | null,
    public image?: string ,
    public priorite?: Priorite,
    public module?: ModulePartenaire
  ) {}
}

export function getPartenaireIdentifier(partenaire: IPartenaire): number | undefined {
  return partenaire.id;
}
