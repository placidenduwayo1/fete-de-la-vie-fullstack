import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { ApplicationConfigService } from '../../core/config/application-config.service'
import { Observable } from 'rxjs'
import { RoleStructure } from '../../model/forum/role-structure.model'

@Injectable({
  providedIn: 'root'
})
export class RoleStructureService {
  private apiUrl = this.applicationConfigService.getEndpointApiFor(
    '/api/roleStructures'
  )
  private apiUrl2 =
    this.applicationConfigService.getEndpointApiFor('/roleStructures')

  constructor (
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  createRoleStructure (roleStructure: RoleStructure): Observable<RoleStructure> {
    return this.http.post<RoleStructure>(this.apiUrl2, roleStructure)
  }

  updateRoleStructure (
    id: number,
    roleStructure: RoleStructure
  ): Observable<RoleStructure> {
    const url = `${this.apiUrl2}/${id}`
    return this.http.put<RoleStructure>(url, roleStructure)
  }

  partialUpdateRoleStructure (
    id: number,
    roleStructure: RoleStructure
  ): Observable<RoleStructure> {
    const url = `${this.apiUrl}/${id}`
    return this.http.patch<RoleStructure>(url, roleStructure, {
      headers: { 'Content-Type': 'application/merge-patch+json' }
    })
  }

  getAllRoleStructures (): Observable<RoleStructure[]> {
    return this.http.get<RoleStructure[]>(this.apiUrl2)
  }

  getRoleStructure (id: number): Observable<RoleStructure> {
    const url = `${this.apiUrl}/${id}`
    return this.http.get<RoleStructure>(url)
  }

  deleteRoleStructure (id: number): Observable<void> {
    const url = `${this.apiUrl2}/${id}`
    return this.http.delete<void>(url)
  }
}
