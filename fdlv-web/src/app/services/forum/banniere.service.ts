import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { ApplicationConfigService } from '../../core/config/application-config.service'
import { Banniere } from '../../model/forum/banniere.model'

@Injectable({
  providedIn: 'root'
})

export class BanniereService {
  private baseUrl = this.applicationConfigService.getEndpointApiFor('/banniere');

  constructor (
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  createBanniere(banniere: any): Observable<any>{
    return this.http.post(this.baseUrl, banniere);
  }

  updateBanniere(banniere: any) {
    const id = banniere.id;
    const url = `${this.baseUrl}/${id}`
    return this.http.put(url, banniere);
  }

  partialUpdateBanniere (
    id: number,
    banniere: Banniere
  ): Observable<Banniere> {
    const url = `${this.baseUrl}/${id}`
    return this.http.patch<Banniere>(url, banniere, {
      headers: { 'Content-Type': 'application/merge-patch+json' }
    })
  }

  getAllBanniere(): Observable<Banniere[]> {
    return this.http.get<Banniere[]>(this.baseUrl);
  }

  getBanniere(id: number): Observable<Banniere> {
    const url = `${this.baseUrl}/${id}`
    return this.http.get<Banniere>(url)
  }

  deleteBanniere(id: number): Observable<void> {
    const url = `${this.baseUrl}/${id}`
    return this.http.delete<void>(url)
  }
}
