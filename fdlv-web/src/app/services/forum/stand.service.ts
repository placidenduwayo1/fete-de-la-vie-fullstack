import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { Stand } from '../../model/forum/stand.model'
import { ApplicationConfigService } from '../../core/config/application-config.service'

@Injectable({
  providedIn: 'root'
})
export class StandService {
  private apiUrl = this.applicationConfigService.getEndpointApiFor('/stands')

  constructor (
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  createStand (stand: Stand): Observable<Stand> {
    return this.http.post<Stand>(this.apiUrl, stand)
  }

  updateStand (id: number, stand: Stand): Observable<Stand> {
    const url = `${this.apiUrl}/${id}`
    return this.http.put<Stand>(url, stand)
  }

  partialUpdateStand (id: number, stand: Stand): Observable<Stand> {
    const url = `${this.apiUrl}/${id}`
    return this.http.patch<Stand>(url, stand, {
      headers: { 'Content-Type': 'application/merge-patch+json' }
    })
  }

  getAllStands (): Observable<Stand[]> {
    return this.http.get<Stand[]>(this.apiUrl);
  }

  getStand (id: number): Observable<Stand> {
    const url = `${this.apiUrl}/${id}`
    return this.http.get<Stand>(url)
  }

  deleteStand (id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`
    return this.http.delete<void>(url)
  }
}
