import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ApplicationConfigService} from "../../core/config/application-config.service";
import {ActeurHabilitationDocument} from "../../model/forum/acteur-habilitation-document.model";

@Injectable({
  providedIn: 'root',
})
export class ActeurHabilitationDocumentService {
  private resourceUrl = this.applicationConfigService.getEndpointApiFor('/api/acteur-habilitation-documents');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  create(acteurHabilitationDocument: ActeurHabilitationDocument): Observable<HttpResponse<ActeurHabilitationDocument>> {
    return this.http.post<ActeurHabilitationDocument>(this.resourceUrl, acteurHabilitationDocument, {observe: 'response'});
  }

  update(acteurHabilitationDocument: ActeurHabilitationDocument): Observable<HttpResponse<ActeurHabilitationDocument>> {
    const url = `${this.resourceUrl}/${acteurHabilitationDocument.id as number}`;
    return this.http.put<ActeurHabilitationDocument>(url, acteurHabilitationDocument, {observe: 'response'});
  }

  partialUpdate(acteurHabilitationDocument: ActeurHabilitationDocument): Observable<HttpResponse<ActeurHabilitationDocument>> {
    const url = `${this.resourceUrl}/${acteurHabilitationDocument.id as number}`;
    return this.http.patch<ActeurHabilitationDocument>(url, acteurHabilitationDocument, {observe: 'response'});
  }

  find(id: number): Observable<HttpResponse<ActeurHabilitationDocument>> {
    const url = `${this.resourceUrl}/${id}`;
    return this.http.get<ActeurHabilitationDocument>(url, {observe: 'response'});
  }

  query(): Observable<HttpResponse<ActeurHabilitationDocument[]>> {
    return this.http.get<ActeurHabilitationDocument[]>(this.resourceUrl, {observe: 'response'});
  }

  delete(id: number): Observable<HttpResponse<void>> {
    const url = `${this.resourceUrl}/${id}`;
    return this.http.delete<void>(url, {observe: 'response'});
  }
}
