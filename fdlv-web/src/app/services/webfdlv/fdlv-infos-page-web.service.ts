import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as dayjs from 'dayjs';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { InfosPageWeb } from '../../model/webfdlv/fdlv-infos-page-web.model';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { StringWrapper } from '../../model/string-wrapper';

export type EntityResponseType = HttpResponse<InfosPageWeb>;
export type EntityArrayResponseType = HttpResponse<InfosPageWeb[]>;

@Injectable({
  providedIn: 'root',
})
export class InfosPageWebService {
  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/info-page-web');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  addInfos(infoPageWeb: InfosPageWeb): Observable<EntityResponseType> {
    return this.http
      .post<InfosPageWeb>(this.resourceUrl, infoPageWeb, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  updateInfo(infoPageWeb: InfosPageWeb): Observable<EntityResponseType> {
    return this.http
      .put<InfosPageWeb>(`${this.resourceUrl}/${infoPageWeb.id}`, infoPageWeb, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<InfosPageWeb>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  getAllInfos(): Observable<EntityArrayResponseType> {
    return this.http
      .get<InfosPageWeb[]>(this.resourceUrl, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<InfosPageWeb> {
    return this.http.delete<InfosPageWeb>(`${this.resourceUrl}/${id}`);
  }

  putImage(image: any, name: string): Observable<HttpResponse<StringWrapper>> {
    const payload = new FormData();
    payload.append('image', image);
    payload.append('name', name);
    return this.http.post<StringWrapper>(`${this.resourceUrl}/image`, payload, { observe: 'response' });
  }

  convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut ? dayjs(res.body.dateDebut) : undefined;
      res.body.dateFin = res.body.dateFin ? dayjs(res.body.dateFin) : undefined;
    }

    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((infoPageWeb: InfosPageWeb) => {
        infoPageWeb.dateDebut = infoPageWeb.dateDebut ? dayjs(infoPageWeb.dateDebut) : undefined;
        infoPageWeb.dateFin = infoPageWeb.dateFin ? dayjs(infoPageWeb.dateFin) : undefined;
      });
    }
    return res;
  }
}
