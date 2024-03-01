import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, zip } from 'rxjs';

import { isPresent } from '../core/utils/operators'; 
import { ApplicationConfigService } from '../core/config/application-config.service'; 
import { IQuizz, getQuizzIdentifier } from '../model/quizz.model';
import { QuestionService, EntityResponseType as QuestionEnttityResponse  } from './question.service';
import { IQuizzVideo } from '../model/quizz-video.model';

export type EntityResponseType = HttpResponse<IQuizz>;
export type EntityResponseVQuizzVideo = HttpResponse<IQuizzVideo[]>;
export type EntityArrayResponseType = HttpResponse<IQuizz[]>;

@Injectable({ providedIn: 'root' })
export class QuizzService {
  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/quizzes');
  public resourceDetailsUrl = this.applicationConfigService.getEndpointApiFor('/quizz');
  public resourceQuizzVideo = this.applicationConfigService.getEndpointApiFor('/videoQuizz');

  constructor(
    protected http: HttpClient,
    private applicationConfigService: ApplicationConfigService,
    private _questionsService: QuestionService
  ) {}

  create(quizz: IQuizz): Observable<EntityResponseType> {
    return this.http.post<IQuizz>(this.resourceUrl, quizz, { observe: 'response' });
  }

  async createSync(quizz: IQuizz): Promise<EntityResponseType> {
    const result = await this.http
      .post<IQuizz>(this.resourceUrl, quizz, { observe: 'response' })
      .toPromise();
    return result;
  }

  update(quizz: IQuizz): Observable<EntityResponseType> {
    return this.http.put<IQuizz>(`${this.resourceUrl}/${getQuizzIdentifier(quizz) as number}`, quizz, { observe: 'response' });
  }

  partialUpdate(quizz: IQuizz): Observable<EntityResponseType> {
    return this.http.patch<IQuizz>(`${this.resourceUrl}/${getQuizzIdentifier(quizz) as number}`, quizz, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuizz>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAll(): Observable<IQuizz[]> {
    return this.http.get<IQuizz[]>(this.resourceUrl);
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  updateDetails(quizz: IQuizz): Observable<EntityResponseType> {
    return this.http.put<IQuizz>(`${this.resourceDetailsUrl}/${getQuizzIdentifier(quizz) as number}`, quizz, { observe: 'response' });
  }

  createDetails(quizz: IQuizz): Observable<EntityResponseType> {
    return this.http.post<IQuizz>(this.resourceDetailsUrl, quizz, { observe: 'response' });
  }

  findDetails(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuizz>(`${this.resourceDetailsUrl}/${id}`, { observe: 'response' });
  }

  findVideoByQuizz(id: number): Observable<EntityResponseVQuizzVideo> {
    return this.http.get<IQuizzVideo[]>(`${this.resourceQuizzVideo}/${id}`, { observe: 'response' });
  }

  addQuizzToCollectionIfMissing(quizzCollection: IQuizz[], ...quizzesToCheck: (IQuizz | null | undefined)[]): IQuizz[] {
    const quizzes: IQuizz[] = quizzesToCheck.filter(isPresent);
    if (quizzes.length > 0) {
      const quizzCollectionIdentifiers = quizzCollection.map(quizzItem => getQuizzIdentifier(quizzItem)!);
      const quizzesToAdd = quizzes.filter(quizzItem => {
        const quizzIdentifier = getQuizzIdentifier(quizzItem);
        if (quizzIdentifier == null || quizzCollectionIdentifiers.includes(quizzIdentifier)) {
          return false;
        }
        quizzCollectionIdentifiers.push(quizzIdentifier);
        return true;
      });
      return [...quizzesToAdd, ...quizzCollection];
    }
    return quizzCollection;
  }

  duplicate(quizz: IQuizz): Observable<EntityResponseType> {
    const id = quizz.id;
    delete quizz.id;
    if (quizz.questions) {
      const currentQuizzQuestions = quizz.questions;
      quizz.questions = [];
      const observableArray = [];
      for (const question of currentQuizzQuestions) {
        observableArray.push(this._questionsService.duplicate(question));
      }
      zip(...observableArray).subscribe(res => {
        res.forEach((er: QuestionEnttityResponse) => {
          if (er.body) {
            quizz.questions!.push(er.body);
          }
        });
      });
    }
    quizz.label = 'D-' + String(id) + '-' + String(quizz.label);
    return this.create(quizz);
  }

  findByLabel(label: string): Observable<HttpResponse<IQuizz>> {
    return this.http.get<IQuizz>(`${this.resourceUrl}/label/${label}`, { observe: 'response' });
  }
}
