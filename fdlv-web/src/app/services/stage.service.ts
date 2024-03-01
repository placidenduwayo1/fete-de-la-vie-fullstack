
import { QuizzService } from './quizz.service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

import { IStage, getStageIdentifier } from '../model/stage.model';
import { catchError } from 'rxjs/operators';
import { ApplicationConfigService } from '../core/config/application-config.service';
import { createRequestOption } from '../core/request/request-util';
import { isPresent } from '../core/utils/operators';

export type EntityResponseType = HttpResponse<IStage>;
export type EntityArrayResponseType = HttpResponse<IStage[]>;

@Injectable({ providedIn: 'root' })
export class StageService {
  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/stages');

  constructor(
    protected http: HttpClient,
    private applicationConfigService: ApplicationConfigService,
    private _quizzService: QuizzService,
    ) {}

  create(stage: IStage): Observable<EntityResponseType> {
    return this.http.post<IStage>(this.resourceUrl, stage, { observe: 'response' });
  }

  async createSync(stage: IStage): Promise<EntityResponseType> {
    const result = await this.http
      .post<IStage>(this.resourceUrl, stage, { observe: 'response' })
      .toPromise();
    return result;
  }

  update(stage: IStage): Observable<EntityResponseType> {
    return this.http.put<IStage>(`${this.resourceUrl}/${getStageIdentifier(stage) as number}`, stage, { observe: 'response' });
  }

  partialUpdate(stage: IStage): Observable<EntityResponseType> {
    return this.http.patch<IStage>(`${this.resourceUrl}/${getStageIdentifier(stage) as number}`, stage, { observe: 'response' });
  }

  find(id: number): Observable<IStage> {
    const url = `${this.resourceUrl}/${id}`;
    return this.http.get<IStage>(url).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 404) {
          return throwError(new Error(`Stage with ID ${id} not found.`));
        } else {
          return throwError(error);
        }
      })
    );
  }

  async findSync(id: number): Promise<any> {
    const result = await this.http
      .get<IStage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .toPromise();
    return result;
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStageToCollectionIfMissing(stageCollection: IStage[], ...stagesToCheck: (IStage | null | undefined)[]): IStage[] {
    const stages: IStage[] = stagesToCheck.filter(isPresent);
    if (stages.length > 0) {
      const stageCollectionIdentifiers = stageCollection.map(stageItem => getStageIdentifier(stageItem)!);
      const stagesToAdd = stages.filter(stageItem => {
        const stageIdentifier = getStageIdentifier(stageItem);
        if (stageIdentifier == null || stageCollectionIdentifiers.includes(stageIdentifier)) {
          return false;
        }
        stageCollectionIdentifiers.push(stageIdentifier);
        return true;
      });
      return [...stagesToAdd, ...stageCollection];
    }
    return stageCollection;
  }

  minuteSecondeToDecimal(coord: string): number {
    if (coord !== '') {
      let buffer = coord.split('°');
      const degrees = Number(buffer[0]);
      buffer = buffer[1].split("'");
      const minutes = Number(buffer[0]);
      const secondes = Number(buffer[1].slice(0, -1));
      console.warn(coord);
      console.warn(degrees);
      console.warn(minutes);
      console.warn(secondes);

      return secondes / 3600 + minutes / 60 + degrees;
    }

    return -1;
  }

  decimalToMinuteSeconde(coord?: number): string {
    let newCoord = '';

    if (coord !== undefined) {
      let buffer: number = coord;

      newCoord += Math.trunc(buffer).toString() + '°';
      buffer = (buffer - Math.trunc(buffer)) * 60;
      newCoord += Math.trunc(buffer).toString() + "'";
      buffer = (buffer - Math.trunc(buffer)) * 60;
      newCoord += buffer.toString() + '"';
    }
    return newCoord;
  }

  /*duplicateStep(stage: IStage): Observable<EntityResponseType> {
    const id = stage.id;
    delete stage.id;
    if (stage.quizz) {
      this._quizzService.duplicate(stage.quizz).subscribe(
        response => (stage.quizz = response.body),
        err => console.warn(err)
      );
    }

    if (stage.challenge) {
      const _challenge = stage.challenge;
      const idChall = _challenge.id;
      delete _challenge.id;
      _challenge.label = 'D-' + String(idChall) + '-' + String(_challenge.label);
      _challenge.description = _challenge.description ? _challenge.description : '';
      this._challengeService.create(_challenge).subscribe(
        response => (stage.challenge = response.body),
        err => console.warn(err)
      );
    }
    stage.label = 'D-' + String(id) + '-' + String(stage.label);
    return this.create(stage);
  }*/
}
