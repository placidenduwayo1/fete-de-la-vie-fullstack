import {Injectable, signal, WritableSignal} from '@angular/core';
import {Theme} from "../model/theme.model";
import {Event} from "../model/event.model";
import {ItemFamilyEnum} from "../enums/item-family.enum";
import {ThemeListMock} from "../mocks/theme-list.mock";
import {Observable, of} from "rxjs";
import {EventDetailMock} from "../mocks/eventDetails.mock";

@Injectable({
  providedIn: 'root'
})
export class StoreService<T> {

  public appStore = {
    gmd: {
      themes: signal<Theme[] | null>(null),
      events: signal<Event[] | null>(null)
    }
  }
  constructor() { }

  getWritableSignal(family: ItemFamilyEnum, subFamily: 'themes' | 'events') {

    return this.appStore[family]?.[subFamily];
  }

  getAll(family: ItemFamilyEnum, subFamily: 'themes' | 'events'): any {
    const writableSignal = this.getWritableSignal(family, subFamily);
    if (writableSignal()?.length) {
      return writableSignal;
    }
    // Todo: Call API here and set result on pipe
    writableSignal.set(EventDetailMock)

    return writableSignal;
  }
}
