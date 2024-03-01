import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, zip } from 'rxjs';

import { isPresent } from '../core/utils/operators';
import { ApplicationConfigService } from '../core/config/application-config.service'; 
import { createRequestOption } from '../core/request/request-util'; 
import { IQuestion, getQuestionIdentifier } from '../model/question.model';
import { AnswerService, EntityResponseType as AnswerEntityResponse } from './answer.service';

export type EntityResponseType = HttpResponse<IQuestion>;
export type EntityArrayResponseType = HttpResponse<IQuestion[]>;

@Injectable({ providedIn: 'root' })
export class QuestionService {
  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/questions');

  constructor(
    protected http: HttpClient,
    private applicationConfigService: ApplicationConfigService,
    private _answerService: AnswerService
  ) {}

  create(question: IQuestion): Observable<EntityResponseType> {
    return this.http.post<IQuestion>(this.resourceUrl, question, { observe: 'response' });
  }

  async createSync(question: IQuestion): Promise<EntityResponseType> {
    const result = await this.http
      .post<IQuestion>(this.resourceUrl, question, { observe: 'response' })
      .toPromise();
    return result;
  }

  update(question: IQuestion): Observable<EntityResponseType> {
    return this.http.put<IQuestion>(`${this.resourceUrl}/${getQuestionIdentifier(question) as number}`, question, { observe: 'response' });
  }

  partialUpdate(question: IQuestion): Observable<EntityResponseType> {
    return this.http.patch<IQuestion>(`${this.resourceUrl}/${getQuestionIdentifier(question) as number}`, question, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuestion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addQuestionToCollectionIfMissing(questionCollection: IQuestion[], ...questionsToCheck: (IQuestion | null | undefined)[]): IQuestion[] {
    const questions: IQuestion[] = questionsToCheck.filter(isPresent);
    if (questions.length > 0) {
      const questionCollectionIdentifiers = questionCollection.map(questionItem => getQuestionIdentifier(questionItem)!);
      const questionsToAdd = questions.filter(questionItem => {
        const questionIdentifier = getQuestionIdentifier(questionItem);
        if (questionIdentifier == null || questionCollectionIdentifiers.includes(questionIdentifier)) {
          return false;
        }
        questionCollectionIdentifiers.push(questionIdentifier);
        return true;
      });
      return [...questionsToAdd, ...questionCollection];
    }
    return questionCollection;
  }

  duplicate(question: IQuestion): Observable<EntityResponseType> {
    const id = question.id;
    delete question.id;
    if (question.answers) {
      const currentQuestionAnswers = question.answers;
      question.answers = [];
      const observableArray = [];
      for (const answer of currentQuestionAnswers) {
        delete answer.id;
        observableArray.push(this._answerService.create(answer));
      }

      zip(...observableArray).subscribe(res => {
        res.forEach((er: AnswerEntityResponse) => {
          if (er.body) {
            question.answers!.push(er.body);
          }
        });
      });
    }
    question.label = 'D-' + String(id) + '-' + String(question.label);
    return this.create(question);
  }
}
