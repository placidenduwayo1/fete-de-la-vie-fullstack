import {HttpClient, HttpResponse} from "@angular/common/http";
import {InterventionStand} from "../../model/forum/intervention-stand.model";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {ApplicationConfigService} from "../../core/config/application-config.service";

@Injectable({
  providedIn: 'root'
})
export class InterventionStandService {
  private resourceUrl = this.applicationConfigService.getEndpointApiFor('/api/interventionStands');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  create(interventionStand: InterventionStand): Observable<InterventionStand> {
    return this.http.post<InterventionStand>(this.resourceUrl, interventionStand);
  }

  update(interventionStand: InterventionStand): Observable<InterventionStand> {
    return this.http.put<InterventionStand>(`${this.resourceUrl}/${interventionStand.fsiId}`, interventionStand);
  }

  partialUpdate(interventionStand: Partial<InterventionStand>): Observable<InterventionStand> {
    return this.http.patch<InterventionStand>(`${this.resourceUrl}/${interventionStand.fsiId}`, interventionStand);
  }

  find(id: number): Observable<InterventionStand> {
    return this.http.get<InterventionStand>(`${this.resourceUrl}/${id}`);
  }

  query(): Observable<InterventionStand[]> {
    return this.http.get<InterventionStand[]>(this.resourceUrl);
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }
}
