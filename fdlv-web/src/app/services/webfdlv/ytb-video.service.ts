import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as dayjs from 'dayjs';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { DATE_TIME_FORMAT } from '../../core/config/input.constants';
import { IYtbVideo, getYtbVideoIdentifier } from '../../model/webfdlv/ytb-video.model';
import { createRequestOption } from '../../core/request/request-util';
export type EntityResponseType = HttpResponse<IYtbVideo>;
export type EntityArrayResponseType = HttpResponse<IYtbVideo[]>;

@Injectable({
  providedIn: 'root',
})
export class YtbVideoService {
  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/ytbVideo');


  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(ytbVideo: IYtbVideo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ytbVideo);
      return this.http
        .post<IYtbVideo>(this.resourceUrl, copy, { observe: 'response' })
        .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(ytbVideo: IYtbVideo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ytbVideo);
    return this.http
      .put<IYtbVideo>(`${this.resourceUrl}/${getYtbVideoIdentifier(ytbVideo) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

   find(id: number): Observable<EntityResponseType> {
    return this.http.get<IYtbVideo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IYtbVideo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(ytbVideo: IYtbVideo): IYtbVideo {
    return Object.assign({}, ytbVideo, {
      dateDebut: ytbVideo.dateDebut ? dayjs(ytbVideo.dateDebut).format(DATE_TIME_FORMAT) : undefined,
      dateFin: ytbVideo.dateFin ?  dayjs(ytbVideo.dateFin).format(DATE_TIME_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateDebut = res.body.dateDebut ? dayjs(res.body.dateDebut) : undefined;
      res.body.dateFin = res.body.dateFin ? dayjs(res.body.dateFin) : undefined;
    }
    
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((ytbVideo: IYtbVideo) => {
        ytbVideo.dateDebut = ytbVideo.dateDebut ? dayjs(ytbVideo.dateDebut) : undefined;
        ytbVideo.dateFin = ytbVideo.dateFin ? dayjs(ytbVideo.dateFin) : undefined;
      });
    }
    return res;
  }

 
}
