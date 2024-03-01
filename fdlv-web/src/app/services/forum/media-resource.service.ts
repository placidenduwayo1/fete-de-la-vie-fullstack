import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { ApplicationConfigService } from 'src/app/core/config/application-config.service';

@Injectable({
  providedIn: 'root',
})
export class MediaResourceService {
  private baseUrl = this.applicationConfigService.getEndpointApiFor('/medias');

  constructor(
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  addPhoto(formData: FormData): Observable<string> {
    return this.http
      .post<string>(`${this.baseUrl}`, formData, {
        responseType: 'text' as 'json',
      })
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse): Observable<any> {
    throw `An error occured - Error code ${error.status}`;
  }
}
