import {Injectable} from "@angular/core";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {PlanningAction} from "../../model/forum/planning-action.model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PlanningActionService {
  private resourceUrl = '/api/planningActions';

  constructor(private http: HttpClient) {
  }

  create(planningAction: PlanningAction): Observable<PlanningAction> {
    return this.http.post<PlanningAction>(this.resourceUrl, planningAction);
  }

  update(planningAction: PlanningAction): Observable<PlanningAction> {
    return this.http.put<PlanningAction>(`${this.resourceUrl}/${planningAction.id}`, planningAction);
  }

  partialUpdate(planningAction: Partial<PlanningAction>): Observable<PlanningAction> {
    return this.http.patch<PlanningAction>(`${this.resourceUrl}/${planningAction.id}`, planningAction);
  }

  find(id: number): Observable<PlanningAction> {
    return this.http.get<PlanningAction>(`${this.resourceUrl}/${id}`);
  }

  query(): Observable<PlanningAction[]> {
    return this.http.get<PlanningAction[]>(this.resourceUrl);
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }
}
