import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { IPartenaire, getPartenaireIdentifier } from '../../model/partenaire.model';
import { StringWrapper } from '../../model/string-wrapper';

export type EntityResponseType = HttpResponse<IPartenaire>;
export type EntityArrayResponseType = HttpResponse<IPartenaire[]>;

@Injectable({ providedIn: 'root' })
export class PartenaireService {
  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/partenaires');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(partenaire: IPartenaire): Observable<EntityResponseType> {
    return this.http.post<IPartenaire>(this.resourceUrl, partenaire, { observe: 'response' });
  }

  async createSync(partenaire: IPartenaire): Promise<EntityResponseType> {
    const result = await this.http
      .post<IPartenaire>(this.resourceUrl, partenaire, { observe: 'response' })
      .toPromise();
    return result;
  }

  update(partenaire: IPartenaire): Observable<EntityResponseType> {
    return this.http.put<IPartenaire>(`${this.resourceUrl}/${getPartenaireIdentifier(partenaire) as number}`, partenaire, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartenaire>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAll(): Observable<EntityArrayResponseType> {
    return this.http.get<IPartenaire[]>(this.resourceUrl, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
  putImage(image: any): Observable<HttpResponse<StringWrapper>> {
    const payload = new FormData();
    payload.append('image', image);
    return this.http.post<StringWrapper>(`${this.resourceUrl}/image`, payload, { observe: 'response' });
  }
}
