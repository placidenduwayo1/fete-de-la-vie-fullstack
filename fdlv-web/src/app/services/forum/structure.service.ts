import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {BehaviorSubject, Observable, catchError} from 'rxjs';
import {ApplicationConfigService} from "../../core/config/application-config.service";
import {Structure} from "../../model/forum/structure.model";
import { NiveauResponsabilite } from '../../model/forum/niveau-responsabilite.model';

@Injectable({
  providedIn: 'root',
})
export class StructureService {
  private baseUrl = this.applicationConfigService.getEndpointApiFor('/structures');

  structures$: Observable<Structure[]>;
  structureSubject = new BehaviorSubject<Structure[]>([]);

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  createStructure(structure: Structure): Observable<Structure> {
    return this.http.post<Structure>(this.baseUrl, structure).pipe(catchError(this.handleError));
  }

  updateStructure(id: number, structure: Structure): Observable<Structure> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.put<Structure>(url, structure).pipe(catchError(this.handleError));
  }

  partialUpdateStructure(id: number, structure: Structure): Observable<Structure> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.patch<Structure>(url, structure, { headers: { 'Content-Type': 'application/merge-patch+json' } });
  }

  getAllStructures(): Observable<Structure[]> {
    return this.http.get<Structure[]>(this.baseUrl).pipe(catchError(this.handleError));
  }

  getStructure(id: number): Observable<Structure> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<Structure>(url).pipe(catchError(this.handleError));
  }

  deleteStructure(id: number): Observable<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete<void>(url).pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse): Observable<any> {
    throw `An error occured - Error code ${error.status}`;
  }
}
