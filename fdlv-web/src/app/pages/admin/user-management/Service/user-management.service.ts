import { Injectable } from '@angular/core'
import { HttpClient, HttpResponse } from '@angular/common/http'
import { ApplicationConfigService } from '../../../../core/config/application-config.service'
import { IUser } from '../../../../model/user.model'
import { Observable } from 'rxjs'
import { Pagination } from 'src/app/core/request/request.model'
import { createRequestOption } from 'src/app/core/request/request-util'

@Injectable({
  providedIn: 'root'
})
export class UserManagementService {
  public resourceUrl =
    this.applicationConfigService.getEndpointApiFor('/admin/users')

  constructor (
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  query(req?: Pagination): Observable<HttpResponse<IUser[]>> {
    const options = createRequestOption(req);
    console.log(options);
    return this.http.get<IUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  findAll () {
    return this.query({page: 0,
      size: 100,
      sort: []});
  }

  create (user: any) {
    return this.http.post(this.resourceUrl, user)
  }
  update (user: IUser): Observable<IUser> {
    return this.http.put<IUser>(this.resourceUrl, user)
  }
  delete (login: any) {
    return this.http.delete(`${this.resourceUrl}/${login}`)
  }
  updateUserInformation (login: any, user: any) {
    return this.http.put(this.resourceUrl, user)
  }
}
