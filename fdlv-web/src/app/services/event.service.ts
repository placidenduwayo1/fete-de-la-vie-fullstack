import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { IEvent, getEventIdentifier } from '../model/event.model';
import { ApplicationConfigService } from '../core/config/application-config.service';
import { DATE_FORMAT } from '../core/config/input.constants';
import { createRequestOption } from '../core/request/request-util';
import { isPresent } from '../core/utils/operators';

export type EntityResponseType = HttpResponse<IEvent>;
export type EntityArrayResponseType = HttpResponse<IEvent[]>;

interface Step {
  id: number;
  title: string;
  order: number;
}
@Injectable({ providedIn: 'root' })
export class EventService {
  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/events');
  public resourceUrlDetails = this.applicationConfigService.getEndpointApiFor('/event-details');
  displayStagePanel = false;
  initialSteps: Step[] = [];
  selectedSteps: Step[] = [];

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(event: IEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(event);
    return this.http
      .post<IEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(event: IEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(event);
    return this.http
      .put<IEvent>(`${this.resourceUrl}/${getEventIdentifier(event) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdateChoixOrganisateur(event: IEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(event);
    return this.http
      .patch<IEvent>(`${this.resourceUrl}/${getEventIdentifier(event) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEvent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  queryDetails(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEvent[]>(this.resourceUrlDetails, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addEventToCollectionIfMissing(eventCollection: IEvent[], ...eventsToCheck: (IEvent | null | undefined)[]): IEvent[] {
    const events: IEvent[] = eventsToCheck.filter(isPresent);
    if (events.length > 0) {
      const eventCollectionIdentifiers = eventCollection.map(eventItem => getEventIdentifier(eventItem)!);
      const eventsToAdd = events.filter(eventItem => {
        const eventIdentifier = getEventIdentifier(eventItem);
        if (eventIdentifier == null || eventCollectionIdentifiers.includes(eventIdentifier)) {
          return false;
        }
        eventCollectionIdentifiers.push(eventIdentifier);
        return true;
      });
      return [...eventsToAdd, ...eventCollection];
    }
    return eventCollection;
  }

  protected convertDateFromClient(event: IEvent): IEvent {
    return Object.assign({}, event, {
    //  startAt: event.startAt?.isValid() ? event.startAt.format(DATE_FORMAT) : undefined,
      //endAt: event.endAt?.isValid() ? event.endAt.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startAt = res.body.startAt ? dayjs(res.body.startAt) : undefined;
      res.body.endAt = res.body.endAt ? dayjs(res.body.endAt) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((event: IEvent) => {
        event.startAt = event.startAt ? dayjs(event.startAt) : undefined;
        event.endAt = event.endAt ? dayjs(event.endAt) : undefined;
      });
    }
    return res;
  }
}
