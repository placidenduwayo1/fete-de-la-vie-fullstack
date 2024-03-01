import { HttpClient, HttpResponse } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { ForumType } from '../../model/forum/forum-type.model';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ForumTypeService {
  private resourceUrl =
    this.applicationConfigService.getEndpointApiFor('/forumTypes');

  forumsA$: Observable<ForumType[]>;
  forumsW$: Observable<ForumType[]>;
  forumSubject = new BehaviorSubject<ForumType[]>([]);
  constructor(
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  findAll(): Observable<ForumType[]> {
    return this.http.get<ForumType[]>(this.resourceUrl + '/all');
  }
}
