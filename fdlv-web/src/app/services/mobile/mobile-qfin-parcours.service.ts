import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { createRequestOption } from 'src/app/core/request/request-util';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'src/app/core/config/application-config.service'; 
import { MobileQuestionFinParcours } from 'src/app/model/mobile-qfin-parcours.model'; 

export type EntityResponseType = HttpResponse<MobileQuestionFinParcours>;
export type EntityArrayResponseType = HttpResponse<MobileQuestionFinParcours[]>;

@Injectable({
  providedIn: 'root',
})
export class MobileQfinParcoursService {
  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/mobile-question-fin-parcours');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(mobileQuestionFinParcours: MobileQuestionFinParcours): Observable<EntityResponseType> {
    return this.http.post<MobileQuestionFinParcours>(`${this.resourceUrl}/`, mobileQuestionFinParcours, { observe: 'response' });
  }

  update(mobileQuestionsFinParcours: MobileQuestionFinParcours): Observable<EntityResponseType> {
    return this.http.put<MobileQuestionFinParcours>(
      `${this.resourceUrl}/${mobileQuestionsFinParcours.id as number}`,
      mobileQuestionsFinParcours,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<MobileQuestionFinParcours>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<MobileQuestionFinParcours[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
