import { HttpClient } from '@angular/common/http'
import { ApplicationConfigService } from '../../core/config/application-config.service'
import { Observable } from 'rxjs'
import { DocumentType } from '../../model/forum/document-type.model'

import { Injectable } from '@angular/core'

@Injectable({
  providedIn: 'root'
})
export class DocumentTypeService {
  private apiUrl =
    this.applicationConfigService.getEndpointApiFor('/documentTypes')

  constructor (
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  createDocumentType (documentType: DocumentType): Observable<DocumentType> {
    return this.http.post<DocumentType>(this.apiUrl, documentType)
  }

  updateDocumentType (
    id: number,
    documentType: DocumentType
  ): Observable<DocumentType> {
    const url = `${this.apiUrl}/${id}`
    return this.http.put<DocumentType>(url, documentType)
  }

  partialUpdateDocumentType (
    id: number,
    documentType: DocumentType
  ): Observable<DocumentType> {
    const url = `${this.apiUrl}/${id}`
    return this.http.patch<DocumentType>(url, documentType, {
      headers: { 'Content-Type': 'application/merge-patch+json' }
    })
  }

  getAllDocumentTypes (): Observable<DocumentType[]> {
    return this.http.get<DocumentType[]>(this.apiUrl)
  }

  getDocumentType (id: number): Observable<DocumentType> {
    const url = `${this.apiUrl}/${id}`
    return this.http.get<DocumentType>(url)
  }

  deleteDocumentType (id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`
    return this.http.delete<void>(url)
  }
}
