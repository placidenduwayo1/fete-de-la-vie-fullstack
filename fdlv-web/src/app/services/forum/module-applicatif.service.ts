import { HttpClient } from '@angular/common/http'
import { ApplicationConfigService } from '../../core/config/application-config.service'
import { ModuleApplicatif } from '../../model/forum/module-applicatif.model'
import { Observable } from 'rxjs'
import { Injectable } from '@angular/core'

@Injectable({
  providedIn: 'root'
})
export class ModuleApplicatifService {
  private apiUrl =
    this.applicationConfigService.getEndpointApiFor('/moduleApplicatifs')

  constructor (
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  createModuleApplicatif (
    moduleApplicatif: ModuleApplicatif
  ): Observable<ModuleApplicatif> {
    return this.http.post<ModuleApplicatif>(this.apiUrl, moduleApplicatif)
  }

  updateModuleApplicatif (
    id: any,
    moduleForum: any
  ): Observable<ModuleApplicatif> {
    const url = `${this.apiUrl}/${id}`
    return this.http.put(url, moduleForum)
  }

  partialUpdateModuleApplicatif (
    id: number,
    moduleApplicatif: ModuleApplicatif
  ): Observable<ModuleApplicatif> {
    const url = `${this.apiUrl}/${id}`
    return this.http.patch<ModuleApplicatif>(url, moduleApplicatif, {
      headers: { 'Content-Type': 'application/merge-patch+json' }
    })
  }

  getAllModuleApplicatifs (): Observable<ModuleApplicatif[]> {
    return this.http.get<ModuleApplicatif[]>(this.apiUrl)
  }

  getModuleApplicatif (id: number): Observable<ModuleApplicatif> {
    const url = `${this.apiUrl}/${id}`
    return this.http.get<ModuleApplicatif>(url)
  }

  deleteModuleApplicatif (id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`
    return this.http.delete<void>(url)
  }
}
