import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as dayjs from 'dayjs';
import { EMPTY, Observable, of } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';
import { FdlvUsers } from '../../model/fdlv-user.model';
import { ApplicationConfigService } from '../../core/config/application-config.service';

export type EntityResponseType = HttpResponse<FdlvUsers>;
export type EntityArrayResponseType = HttpResponse<FdlvUsers[]>;
export type StringArrayResponseType = HttpResponse<string[]>;

@Injectable({
  providedIn: 'root',
})
export class FdlvUserService {
  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/fdlv-user');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(fdlvUser: FdlvUsers): Observable<EntityResponseType> {
    return this.http
      .post<FdlvUsers>(`${this.resourceUrl}/`, fdlvUser, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(fdlvUsers: FdlvUsers): Observable<EntityResponseType> {
    return this.http
      .put<FdlvUsers>(`${this.resourceUrl}/${fdlvUsers.id as number}`, fdlvUsers, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<FdlvUsers>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findAll(): Observable<EntityArrayResponseType> {
    return this.http
      .get<FdlvUsers[]>(this.resourceUrl, { observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  updateActive(fdlvUser: any): Observable<EntityResponseType> {
    return this.http
      .put<FdlvUsers>(`${this.resourceUrl}/activate/${fdlvUser.id as number}`, fdlvUser, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  sendMail(fdlvUser: FdlvUsers): Observable<any> {
    return this.http.post(`${this.resourceUrl}/reset/${fdlvUser.id as number}`, fdlvUser);
  }

  getUser(fusId: number): Observable<FdlvUsers> {
    return this.find(fusId).pipe(
      mergeMap((fdlvUser: HttpResponse<FdlvUsers>) => {
        if (fdlvUser.body) {
          return of(fdlvUser.body);
        } else {
          return EMPTY;
        }
      })
    );
  }

  getUserLogin = async (fusId: number): Promise<string> => {
    let userLogin = 'UNKNOWN';
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-condition
    if (fusId === null) {
      fusId = 2;
    }

    const fdlvUser = await this.getUser(fusId).toPromise();
    if (fdlvUser.login) {
      userLogin = fdlvUser.login;
    }
    return userLogin;
  };

  protected convertDateFromClient(fdlvUser: FdlvUsers): FdlvUsers {
    return Object.assign({}, fdlvUser, {
      dateDebut: fdlvUser.dateDebut?.isValid() ? fdlvUser.dateDebut.format('DD/MM/YYYY') : undefined,
      dateFin: fdlvUser.dateFin?.isValid() ? fdlvUser.dateFin.format('DD/MM/YYYY') : undefined,
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
      res.body.forEach((fdlvUser: FdlvUsers) => {
        fdlvUser.dateDebut = fdlvUser.dateDebut ? dayjs(fdlvUser.dateDebut) : undefined;
        fdlvUser.dateFin = fdlvUser.dateFin ? dayjs(fdlvUser.dateFin) : undefined;
      });
    }
    return res;
  }
}
