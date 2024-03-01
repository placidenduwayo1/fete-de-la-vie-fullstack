import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ActeurHabilitationModule} from "../../model/forum/acteur-habilitation-module.model";
import {ApplicationConfigService} from "../../core/config/application-config.service";

@Injectable({
  providedIn: 'root'
})
export class ActeurHabilitationModuleService {
  private apiUrl = this.applicationConfigService.getEndpointApiFor('/api/acteur-habilitation-modules');

  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  createActeurHabilitationModule(acteurHabilitationModule: ActeurHabilitationModule): Observable<ActeurHabilitationModule> {
    return this.http.post<ActeurHabilitationModule>(this.apiUrl, acteurHabilitationModule);
  }

  updateActeurHabilitationModule(id: number, acteurHabilitationModule: ActeurHabilitationModule): Observable<ActeurHabilitationModule> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.put<ActeurHabilitationModule>(url, acteurHabilitationModule);
  }

  partialUpdateActeurHabilitationModule(id: number, acteurHabilitationModule: ActeurHabilitationModule): Observable<ActeurHabilitationModule> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.patch<ActeurHabilitationModule>(url, acteurHabilitationModule, {headers: {'Content-Type': 'application/merge-patch+json'}});
  }

  getAllActeurHabilitationModules(): Observable<ActeurHabilitationModule[]> {
    return this.http.get<ActeurHabilitationModule[]>(this.apiUrl);
  }

  getActeurHabilitationModule(id: number): Observable<ActeurHabilitationModule> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<ActeurHabilitationModule>(url);
  }

  deleteActeurHabilitationModule(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
