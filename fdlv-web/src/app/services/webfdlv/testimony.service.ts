import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { createRequestOption } from '../../core/request/request-util';
import * as dayjs from 'dayjs';
import { Observable , catchError, tap} from 'rxjs';
import { map } from 'rxjs/operators';
import { ITestimony, Testimony, getTestimonyIdentifier } from '../../model/webfdlv/testimony.model';
import { DATE_TIME_FORMAT, API_FDLV_URL} from '../../core/config/input.constants';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { StringWrapper } from 'src/app/model/string-wrapper';

export type EntityResponseType = HttpResponse<ITestimony>;
export type EntityArrayResponseType = HttpResponse<ITestimony[]>;

@Injectable({
  providedIn: 'root',
})
/*export class TestimonyService {
  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/testimony');

  constructor(protected http: HttpClient,
    private applicationConfigService: ApplicationConfigService
) {}

  create(testimony: ITestimony): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testimony);
    console.log("$$$$$$$$$$$", copy)
    return this.http
      .post<ITestimony>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(testimony: ITestimony): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testimony);
    return this.http
      .put<ITestimony>(`${this.resourceUrl}/${getTestimonyIdentifier(testimony) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITestimony>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITestimony[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

 /* protected convertDateFromClient(testimony: ITestimony): ITestimony {
    return Object.assign({}, testimony, {
      dateDebut: testimony.dateDebut?.isValid() ? testimony.dateDebut.format(DATE_TIME_FORMAT) : undefined,
      dateFin: testimony.dateFin?.isValid() ? testimony.dateFin.format(DATE_TIME_FORMAT) : undefined,
    });
  }*/
/*
  protected convertDateFromClient(testimony: ITestimony): ITestimony {
    return Object.assign({}, testimony, {
      dateDebut: testimony.dateDebut ? dayjs(testimony.dateDebut).format(DATE_TIME_FORMAT) : undefined,
      dateFin: testimony.dateFin ?  dayjs(testimony.dateFin).format(DATE_TIME_FORMAT) : undefined,
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
      res.body.forEach((testimony: ITestimony) => {
        testimony.dateDebut = testimony.dateDebut ? dayjs(testimony.dateDebut) : undefined;
        testimony.dateFin = testimony.dateFin ? dayjs(testimony.dateFin) : undefined;
      });
    }
    return res;
  }


}*/
export class TestimonyService {
  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/testimony');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(testimony: Testimony): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testimony);
    return this.http
      .post<Testimony>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(testimony: Testimony): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(testimony);
    return this.http
      .put<Testimony>(`${this.resourceUrl}/${getTestimonyIdentifier(testimony) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<Testimony>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<Testimony[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  putImage(image: any, name: string, lienAuteur: any): Observable<HttpResponse<StringWrapper>> {
    const payload = new FormData();
    payload.append('image', image);
    payload.append('name', name);
    payload.append('lienAuteur', lienAuteur);
    //delete existed img with same name
   // this.http.delete(`${this.resourceUrl}/image`, { observe: 'response' });
    return this.http.post<StringWrapper>(`${this.resourceUrl}/image`, payload, { observe: 'response' });
  }
  protected convertDateFromClient(testimony: ITestimony): ITestimony {
    return Object.assign({}, testimony, {
      dateDebut: testimony.dateDebut ? dayjs(testimony.dateDebut).format(DATE_TIME_FORMAT) : undefined,
      dateFin: testimony.dateFin ?  dayjs(testimony.dateFin).format(DATE_TIME_FORMAT) : undefined,
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
      res.body.forEach((testimony: Testimony) => {
        testimony.dateDebut = testimony.dateDebut ? dayjs(testimony.dateDebut) : undefined;
        testimony.dateFin = testimony.dateFin ? dayjs(testimony.dateFin) : undefined;
      });
    }
    return res;
  }
}

