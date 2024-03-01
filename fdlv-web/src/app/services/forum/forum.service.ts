import { HttpClient, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { Forum } from '../../model/forum/forum.model';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ForumService {
  private resourceUrl =
    this.applicationConfigService.getEndpointApiFor('/forums');

  forumsA$: Observable<Forum[]>;
  forumsW$: Observable<Forum[]>;
  forumSubject = new BehaviorSubject<Forum[]>([]);
  constructor(
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  create(forum: Forum): Observable<HttpResponse<Forum>> {
    return this.http.post<Forum>(this.resourceUrl, forum, {
      observe: 'response',
    });
  }
  update(id: number, forum: Forum): Observable<HttpResponse<Forum>> {
    return this.http.put<Forum>(`${this.resourceUrl}/${id as number}`, forum, {
      observe: 'response',
    });
  }

  partialUpdate(forum: Forum): Observable<HttpResponse<Forum>> {
    return this.http.patch<Forum>(
      `${this.resourceUrl}/${forum.id as number}`,
      forum,
      { observe: 'response' }
    );
  }

  findAll(): Observable<Forum[]> {
    return this.http.get<Forum[]>(this.resourceUrl + '/all');
  }
  findAllWithCorrespondantFDLV(): Observable<Forum[]> {
    return this.http.get<Forum[]>(
      this.resourceUrl + '/with-correspondant-fdlv'
    );
  }
  findOne(id: number): Observable<HttpResponse<Forum>> {
    return this.http.get<Forum>(`${this.resourceUrl}/${id}`, {
      observe: 'response',
    });
  }

  delete(id: number): Observable<HttpResponse<void>> {
    return this.http.delete<void>(`${this.resourceUrl}/${id}`, {
      observe: 'response',
    });
  }
}
