import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { environment } from 'src/environments/environment'
import { Observable } from 'rxjs'
import { MediaUploadModel } from '../../model/forum/media-upload.model'
import { MediaResponse } from '../../model/forum/media-Response.model'
import { ApplicationConfigService } from 'src/app/core/config/application-config.service'

@Injectable({
  providedIn: 'root'
})
export class MediasService {
  private apiUrl = this.applicationConfigService.getEndpointApiFor('/medias')

  constructor (
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  uploadMedia (mediaData: MediaUploadModel): Observable<MediaResponse> {
    const formData = new FormData()
    formData.append('media', mediaData.media)
    formData.append('name', mediaData.name)
    formData.append('path', mediaData.path)

    return this.http.post<MediaResponse>(this.apiUrl, formData)
  }
}
