import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { catchError } from 'rxjs/operators';
import { FdlvVideo } from '../../model/webfdlv/fdlv-video.model';

export type EntityResponseType = HttpResponse<FdlvVideo>;
export type EntityArrayResponseType = HttpResponse<FdlvVideo[]>;

@Injectable({
  providedIn: 'root',
})
export class FdlvVideoService {
  public resourceUrl = this.applicationConfigService.getEndpointApiFor('/liste-videos');
  

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(fdlvVideo: FdlvVideo): Observable<EntityResponseType> {
    return this.http.post<FdlvVideo>(this.resourceUrl, fdlvVideo, { observe: 'response' });
  }

  update(fdlvVideo: FdlvVideo): Observable<EntityResponseType> {
    return this.http.put<FdlvVideo>(`${this.resourceUrl}/${fdlvVideo.id as number}`, fdlvVideo, {
      observe: 'response',
    });
  }

  updateVideoQuizz(quizzIDlist: number[], idVideo: number): Observable<EntityResponseType> {
    return this.http.put<FdlvVideo>(`${this.resourceUrl}/${idVideo}/quizz`, quizzIDlist, {
      observe: 'response',
    });
  }

  find(id: number): Observable<FdlvVideo> {
    const url = `${this.resourceUrl}/${id}`;
    return this.http.get<FdlvVideo>(url).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 404) {
          return throwError(new Error(`Video with ID ${id} not found.`));
        } else {
          return throwError(error);
        }
      })
    );
  }

  getListeQuizzVideosID(id: number): Observable<EntityResponseType> {
    return this.http.get<FdlvVideo>(`${this.resourceUrl}/${id}/quizz`, { observe: 'response' });
  }

  findAll(): Observable<EntityArrayResponseType> {
    return this.http.get<FdlvVideo[]>(this.resourceUrl, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findByUrl(url: string): Observable<FdlvVideo> {
    const urlWithParams = `${this.resourceUrl}/findByUrl`;
    return this.http
      .post<FdlvVideo>(urlWithParams, { url })
      .pipe(
        catchError((error: HttpErrorResponse) => {
          if (error.status === 404) {
            return throwError(new Error(`Video with URL ${url} not found.`));
          } else {
            return throwError(error);
          }
        })
      );
  }
}
