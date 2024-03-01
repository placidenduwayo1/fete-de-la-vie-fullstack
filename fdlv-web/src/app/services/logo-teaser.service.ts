import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LogoTeaser, getlogoteaserIdentifier } from '../model/logo-teaser.model';
import { ApplicationConfigService } from '../core/config/application-config.service';
import { Observable, firstValueFrom, map } from 'rxjs';
import { StringWrapper } from '../model/string-wrapper';


export type EntityResponseType = HttpResponse<LogoTeaser>;
export type EntityArrayResponseType = HttpResponse<LogoTeaser[]>;

@Injectable({
  providedIn: 'root'
})
export class LogoTeaserService {

  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/logo-teaser');
  private prefixUrl ="https://www.gomydefi.live/fdlv/";

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(logoteaser: LogoTeaser): Observable<EntityResponseType> {
    return this.http.post<LogoTeaser>(this.resourceUrl, logoteaser, { observe: 'response' });
  }

  async createSync(logoteaser: LogoTeaser): Promise<EntityResponseType> {
    return await firstValueFrom(this.http
      .post<LogoTeaser>(this.resourceUrl, logoteaser, { observe: 'response' }));
  }

  update(logoteaser: LogoTeaser): Observable<EntityResponseType> {
    return this.http.put<LogoTeaser>(`${this.resourceUrl}/${getlogoteaserIdentifier(logoteaser) as number}`, logoteaser, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<LogoTeaser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAll(): Observable<EntityArrayResponseType> {
    return this.http.get<LogoTeaser[]>(this.resourceUrl, { observe: 'response' })
    .pipe(map((res: EntityArrayResponseType) => this.convertUrlFromServer(res)));;
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  postFile(file: File,typeMedia: string): Observable<HttpResponse<StringWrapper>> {
    const payload = new FormData();
    payload.append('media', file);
    return this.http.post<StringWrapper>(`${this.resourceUrl}/media/${typeMedia.toLowerCase()}`, payload, { observe: 'response' });
  }


  protected convertUrlFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((logo: LogoTeaser) => {
        logo.url = logo.url ? logo.url.replace(this.prefixUrl,"") : undefined;
      });
    }
    return res;
  }
}
