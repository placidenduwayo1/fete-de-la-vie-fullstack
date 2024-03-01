import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { BehaviorSubject, Observable, catchError, tap, throwError } from 'rxjs';
import { Acteur } from '../../model/forum/acteur.model';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { User } from 'src/app/model/user.model';

@Injectable({
  providedIn: 'root',
})
export class ActeurService {
  private baseUrl = this.applicationConfigService.getEndpointApiFor('/acteurs');

  acteurs$: Observable<Acteur[]>;
  acteurSubject = new BehaviorSubject<Acteur[]>([]);

  constructor(
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  createActeur(acteur: Acteur): Observable<Acteur> {
    return this.http
      .post<Acteur>(this.baseUrl, acteur)
      .pipe(catchError(this.handleError));
  }

  addPhoto(data: FormData): Observable<boolean> {
    return this.http
      .post<boolean>(`${this.baseUrl}/photo`, data)
      .pipe(catchError(this.handleError));
  }

  updateActeur(id: number, acteur: Acteur): Observable<Acteur> {
    const url = `${this.baseUrl}/${id}`;
    return this.http
      .put<Acteur>(url, acteur)
      .pipe(catchError(this.handleError));
  }

  activerDesactiverActeur(id: number): Observable<Acteur> {
    const url = `${this.baseUrl}/maj-statut/${id}`;
    return this.http.put<Acteur>(url, null).pipe(catchError(this.handleError));
  }

  partialUpdateActeur(id: number, acteur: Acteur): Observable<Acteur> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.patch<Acteur>(url, acteur, {
      headers: { 'Content-Type': 'application/merge-patch+json' },
    });
  }

  getAllActeurs(): Observable<Acteur[]> {
    return this.http
      .get<Acteur[]>(this.baseUrl)
      .pipe(tap(response=> response.forEach(acteur=> console.log(acteur?.forum?.codePostal))),catchError(this.handleError));
  }

  getResponsables(): Observable<Acteur[]> {
    return this.http
      .get<Acteur[]>(`${this.baseUrl}/acteurs-responsable`)
      .pipe(catchError(this.handleError));
  }

  getActeur(id: number): Observable<Acteur> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.get<Acteur>(url).pipe(catchError(this.handleError));
  }

  getUserConnected(): Observable<User> {
    const url = `${this.baseUrl}/user-connected`;
    return this.http.get<User>(url).pipe(catchError(this.handleError));
  }

  deleteActeur(id: number): Observable<void> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete<void>(url).pipe(catchError(this.handleError));
  }
  private handleError(error: HttpErrorResponse): Observable<any> {
    throw `An error occured - Error code ${error.status}`;
  }
}
