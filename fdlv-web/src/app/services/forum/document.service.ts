import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ApplicationConfigService} from "../../core/config/application-config.service";
import {Document} from "../../model/forum/document.model";


@Injectable({
  providedIn: 'root',
})
export class DocumentService {
  private baseUrl = this.applicationConfigService.getEndpointApiFor('/api/documents');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  createDocument(document: Document): Observable<Document> {
    return this.http.post<Document>(this.baseUrl, document);
  }

  updateDocument(id: number, document: Document): Observable<Document> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.put<Document>(url, document);
  }

  partialUpdateDocument(id: number, document: Document): Observable<Document> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.patch<Document>(url, document, {headers: {'Content-Type': 'application/merge-patch+json'}});
  }

  getAllDocuments(): Observable<Document[]> {
    return this.http.get<Document[]>(this.baseUrl);
  }

  getDocument(id: number): Observable<Document> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<Document>(url);
  }

  deleteDocument(id: number): Observable<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
