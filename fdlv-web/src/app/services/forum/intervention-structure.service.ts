import {Injectable, inject} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {InterventionStructure} from "../../model/forum/intervention-structure.model";
import {Observable} from "rxjs";
import {ApplicationConfigService} from "../../core/config/application-config.service";
import { ActivatedRouteSnapshot, ResolveFn } from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class InterventionStructureService {
  private apiUrl = this.applicationConfigService.getEndpointApiFor('/interventionStructures');
  constructor(private http: HttpClient, private applicationConfigService: ApplicationConfigService) {
  }

  createInterventionStructure(interventionStructure: InterventionStructure): Observable<InterventionStructure> {
    return this.http.post<InterventionStructure>(this.apiUrl, interventionStructure);
  }

  updateInterventionStructure(id: number, interventionStructure: InterventionStructure): Observable<InterventionStructure> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.put<InterventionStructure>(url, interventionStructure);
  }

  partialUpdateInterventionStructure(id: number, interventionStructure: InterventionStructure): Observable<InterventionStructure> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.patch<InterventionStructure>(url, interventionStructure, {headers: {'Content-Type': 'application/merge-patch+json'}});
  }

  getAllInterventionStructures(): Observable<InterventionStructure[]> {
    return this.http.get<InterventionStructure[]>(this.apiUrl);
  }

  getInterventionStructure(id: number): Observable<InterventionStructure> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<InterventionStructure>(url);
  }

  deleteInterventionStructure(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }

  //new modif debut
  getInterventionForStructure(structureId: number): Observable<Array<InterventionStructure>>{
    const url = `${this.apiUrl}/intervention-structure/${structureId}`;
    return this.http.get<Array<InterventionStructure>>(url);
  }
  //new modif dfin
}
//new modif debut
export const GetInterventionForStructureResolve: ResolveFn<InterventionStructure[]> = (route: ActivatedRouteSnapshot) =>{
  return inject(InterventionStructureService).getInterventionForStructure(+route.paramMap.get('structureId'));
}
//new modif fin
