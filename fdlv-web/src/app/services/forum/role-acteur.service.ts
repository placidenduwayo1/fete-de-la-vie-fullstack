import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { ApplicationConfigService } from '../../core/config/application-config.service'
import { RoleActeur } from '../../model/forum/role-acteur.model'

@Injectable({
  providedIn: 'root'
})
export class RoleActeurService {
  private baseUrl =
    this.applicationConfigService.getEndpointApiFor('/role-acteurs')

  constructor (
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  createRoleActeur (roleActeur): Observable<RoleActeur> {
    return this.http.post(this.baseUrl, roleActeur)
  }
  create (roleActeur: any) {
    return this.http.post(this.baseUrl, roleActeur)
  }

  // updateRoleActeur (id: any, roleActeur: any) {
  //   const url = `${this.baseUrl}/${id}`
  //   return this.http.put<RoleActeur>(url, roleActeur)
  // }
  updateRoleActeur (id: any, roleActeur: any) {
    const url = `${this.baseUrl}/${id}`
    return this.http.put(url, roleActeur)
  }

  partialUpdateRoleActeur (
    id: number,
    roleActeur: RoleActeur
  ): Observable<RoleActeur> {
    const url = `${this.baseUrl}/${id}`
    return this.http.patch<RoleActeur>(url, roleActeur, {
      headers: { 'Content-Type': 'application/merge-patch+json' }
    })
  }

  getAllRoleActeurs (): Observable<RoleActeur[]> {
    return this.http.get<RoleActeur[]>(this.baseUrl)
  }

  getRoleActeur (id: number): Observable<RoleActeur> {
    const url = `${this.baseUrl}/${id}`
    return this.http.get<RoleActeur>(url)
  }

  deleteRoleActeur (id: number): Observable<void> {
    const url = `${this.baseUrl}/${id}`
    return this.http.delete<void>(url)
  }
}
