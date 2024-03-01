import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { ApplicationConfigService } from '../../core/config/application-config.service'
import { ForumTheme } from '../../model/forum/forum-theme.model'

@Injectable({
  providedIn: 'root'
})

export class ForumThemeService {
  private baseUrl =
    this.applicationConfigService.getEndpointApiFor('/forum-theme')

  constructor (
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  createForumTheme (forumTheme): Observable<any> {
    return this.http.post(this.baseUrl, forumTheme);
  }

  updateForumTheme (ForumTheme: any) {
    const id = ForumTheme.id;
    const url = `${this.baseUrl}/${id}`
    return this.http.put(url, ForumTheme)
  }

  partialUpdateForumTheme (
    id: number,
    ForumTheme: ForumTheme
  ): Observable<ForumTheme> {
    const url = `${this.baseUrl}/${id}`
    return this.http.patch<ForumTheme>(url, ForumTheme, {
      headers: { 'Content-Type': 'application/merge-patch+json' }
    })
  }

  getAllForumTheme (): Observable<ForumTheme[]> {
    return this.http.get<ForumTheme[]>(this.baseUrl)
  }

  getForumTheme (id: number): Observable<ForumTheme> {
    const url = `${this.baseUrl}/${id}`
    return this.http.get<ForumTheme>(url)
  }

  deleteForumTheme (id: number): Observable<void> {
    const url = `${this.baseUrl}/${id}`
    return this.http.delete<void>(url)
  }
}
