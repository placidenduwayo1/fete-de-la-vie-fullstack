import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {ApplicationConfigService} from "../../core/config/application-config.service";
import {InterventionActeur} from "../../model/forum/intervention-acteur.model";
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class InterventionActeurService {
  private resourceUrl = this.applicationConfigService.getEndpointApiFor('/interventionActeurs');
  interventionActeur$: Observable<InterventionActeur[]>;
  interventionActeurSubject = new BehaviorSubject<InterventionActeur[]>([]);
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  create(interventionActeur: InterventionActeur): Observable<InterventionActeur> {
    return this.http.post<InterventionActeur>(this.resourceUrl, interventionActeur, { headers: { 'Content-Type': 'application/json' } });
  }

  update(interventionActeur: InterventionActeur): Observable<InterventionActeur> {
    return this.http.put<InterventionActeur>(`${this.resourceUrl}/${interventionActeur.finId}`, interventionActeur);
  }

  partialUpdate(interventionActeur: Partial<InterventionActeur>): Observable<InterventionActeur> {
    return this.http.patch<InterventionActeur>(`${this.resourceUrl}/${interventionActeur.finId}`, interventionActeur);
}

  find(id: number): Observable<InterventionActeur> {
    return this.http.get<InterventionActeur>(`${this.resourceUrl}/${id}`);
  }

  query(): Observable<InterventionActeur[]> {
    return this.http.get<InterventionActeur[]>(this.resourceUrl);
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }
}
