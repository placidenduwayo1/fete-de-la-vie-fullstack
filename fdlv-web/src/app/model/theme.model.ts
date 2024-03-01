import { IEvent } from './event.model';

export interface ITheme {
  id?: number;
  label?: string;
  events?: IEvent[] | null;
}

export class Theme implements ITheme {
  constructor(public id?: number, public label?: string, public events?: IEvent[] | null) {}
}

export function getThemeIdentifier(theme: ITheme): number | undefined {
  return theme.id;
}
