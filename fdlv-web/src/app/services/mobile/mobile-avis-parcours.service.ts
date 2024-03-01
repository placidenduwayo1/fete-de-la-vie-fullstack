import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { createRequestOption } from 'src/app/core/request/request-util'; 
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'src/app/core/config/application-config.service'; 
import { MobileAvisParcours } from 'src/app/model/mobile-avis-parcours.model'; 

export type EntityResponseType = HttpResponse<MobileAvisParcours>;
export type EntityArrayResponseType = HttpResponse<MobileAvisParcours[]>;

@Injectable({
  providedIn: 'root',
})
export class MobileAvisParcoursService {
  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/mobile-avis-parcours');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(mobileAvisParcours: MobileAvisParcours): Observable<EntityResponseType> {
    return this.http.post<MobileAvisParcours>(`${this.resourceUrl}/`, mobileAvisParcours, { observe: 'response' });
  }

  update(mobileAvisFinParcours: MobileAvisParcours): Observable<EntityResponseType> {
    return this.http.put<MobileAvisParcours>(`${this.resourceUrl}/${mobileAvisFinParcours.id as number}`, mobileAvisFinParcours, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<MobileAvisParcours>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<MobileAvisParcours[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
