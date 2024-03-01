import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { createRequestOption } from 'src/app/core/request/request-util';
import * as dayjs from 'dayjs';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { MobileUser } from 'src/app/model/mobile-user.model'; 
import { ApplicationConfigService } from 'src/app/core/config/application-config.service'; 

export type EntityResponseType = HttpResponse<MobileUser>;
export type EntityArrayResponseType = HttpResponse<MobileUser[]>;

@Injectable({
  providedIn: 'root',
})
export class MobileUserService {
  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/mobile-user');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(mobileUser: MobileUser): Observable<EntityResponseType> {
    return this.http
      .post<MobileUser>(`${this.resourceUrl}/`, mobileUser, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mobileUsers: MobileUser): Observable<EntityResponseType> {
    return this.http
      .put<MobileUser>(`${this.resourceUrl}/${mobileUsers.id as number}`, mobileUsers, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<MobileUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<MobileUser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(mobileUser: MobileUser): MobileUser {
    return Object.assign({}, mobileUser, {
      dateFermeture: mobileUser.dateFermeture?.isValid() ? mobileUser.dateFermeture.format('YYYY/MM/DD HH:mm:ss') : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateFermeture = res.body.dateFermeture ? dayjs(res.body.dateFermeture) : undefined;
    }

    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((mobileUser: MobileUser) => {
        mobileUser.dateFermeture = mobileUser.dateFermeture ? dayjs(mobileUser.dateFermeture) : undefined;
      });
    }
    return res;
  }
}
