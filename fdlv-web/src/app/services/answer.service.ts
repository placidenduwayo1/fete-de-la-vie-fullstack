import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from '../core/config/application-config.service';
import { IAnswer, getAnswerIdentifier } from '../model/answer.model';
import { Observable, firstValueFrom } from 'rxjs';
import { createRequestOption } from '../core/request/request-util';
import { isPresent } from '../core/utils/operators';

export type EntityResponseType = HttpResponse<IAnswer>;
export type EntityArrayResponseType = HttpResponse<IAnswer[]>;

@Injectable({
  providedIn: 'root'
})
export class AnswerService {

  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/answeres');
  public resourceDetailUrl = this.applicationConfigService.getEndpointApiFor('/answer');
  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(answer: IAnswer): Observable<EntityResponseType> {
    return this.http.post<IAnswer>(this.resourceUrl, answer, { observe: 'response' });
  }

  update(answer: IAnswer): Observable<EntityResponseType> {
    return this.http.put<IAnswer>(`${this.resourceUrl}/${getAnswerIdentifier(answer) as number}`, answer, { observe: 'response' });
  }

  partialUpdate(answer: IAnswer): Observable<EntityResponseType> {
    return this.http.patch<IAnswer>(`${this.resourceUrl}/${getAnswerIdentifier(answer) as number}`, answer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnswer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findDetail(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnswer>(`${this.resourceDetailUrl}/${id}`, { observe: 'response' });
  }

  findAll(): Observable<EntityArrayResponseType> {
    return this.http.get<IAnswer[]>(this.resourceUrl, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnswer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  async querySync(req?: any): Promise<any> {
    const options = createRequestOption(req);
    const result = await firstValueFrom(this.http
      .get<IAnswer[]>(this.resourceUrl, { params: options, observe: 'response' }));
    return result;
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAnswerToCollectionIfMissing(answerCollection: IAnswer[], ...answersToCheck: (IAnswer | null | undefined)[]): IAnswer[] {
    const answers: IAnswer[] = answersToCheck.filter(isPresent);
    if (answers.length > 0) {
      // eslint-disable-next-line @typescript-eslint/no-unsafe-return
      const answerCollectionIdentifiers = answerCollection.map(answerItem => getAnswerIdentifier(answerItem)!);
      const answersToAdd = answers.filter(answerItem => {
        const answerIdentifier = getAnswerIdentifier(answerItem);
        if (answerIdentifier == null || answerCollectionIdentifiers.includes(answerIdentifier)) {
          return false;
        }
        answerCollectionIdentifiers.push(answerIdentifier);
        return true;
      });
      return [...answersToAdd, ...answerCollection];
    }
    return answerCollection;
  }
}
