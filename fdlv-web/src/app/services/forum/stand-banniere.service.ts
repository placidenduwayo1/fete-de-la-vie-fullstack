import { HttpClient } from '@angular/common/http'
import { ApplicationConfigService } from '../../core/config/application-config.service'
import { Observable } from 'rxjs'
import { StandBanniere } from '../../model/forum/stand-banniere.model'
import { Injectable } from '@angular/core'

@Injectable({
  providedIn: 'root'
})
export class StandBanniereService {
  private apiUrl =
    this.applicationConfigService.getEndpointApiFor('/stand-bannieres')

  constructor (
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  createStandBanniere (standBanniere: StandBanniere): Observable<StandBanniere> {
    return this.http.post<StandBanniere>(this.apiUrl, standBanniere)
  }

  updateStandBanniere (
    id: number,
    standBanniere: StandBanniere
  ): Observable<StandBanniere> {
    const url = `${this.apiUrl}/${id}`
    return this.http.put<StandBanniere>(url, standBanniere)
  }

  partialUpdateStandBanniere (
    id: number,
    standBanniere: StandBanniere
  ): Observable<StandBanniere> {
    const url = `${this.apiUrl}/${id}`
    return this.http.patch<StandBanniere>(url, standBanniere, {
      headers: { 'Content-Type': 'application/merge-patch+json' }
    })
  }

  getAllStandBannieres (): Observable<StandBanniere[]> {
    return this.http.get<StandBanniere[]>(this.apiUrl)
  }

  getStandBanniere (id: number): Observable<StandBanniere> {
    const url = `${this.apiUrl}/${id}`
    return this.http.get<StandBanniere>(url)
  }

  deleteStandBanniere (id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`
    return this.http.delete<void>(url)
  }
}
