import { HttpClient, HttpHeaders } from '@angular/common/http';
import { ApplicationConfigService } from '../../core/config/application-config.service';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { StandSecteur } from '../../model/forum/stand-secteur.model';

@Injectable({
  providedIn: 'root',
})
export class StandSecteurService {
  private apiUrl =
    this.applicationConfigService.getEndpointApiFor('/stand-secteurs');
  constructor(
    private http: HttpClient,
    private applicationConfigService: ApplicationConfigService
  ) {}

  createStandSecteur(standSecteur: StandSecteur): Observable<StandSecteur> {
    return this.http.post<StandSecteur>(this.apiUrl, standSecteur, {headers: { 'Content-Type': 'application/json' }});
  }

  updateStandSecteur(
    id: number,
    standSecteur: StandSecteur
  ): Observable<StandSecteur> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.put<StandSecteur>(url, standSecteur);
  }

  partialUpdateStandSecteur(
    id: number,
    standSecteur: StandSecteur
  ): Observable<StandSecteur> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.patch<StandSecteur>(url, standSecteur, {
      headers: { 'Content-Type': 'application/merge-patch+json' },
    });
  }

  getAllStandSecteurs(): Observable<StandSecteur[]> {
    return this.http.get<StandSecteur[]>(this.apiUrl);
  }

  getStandSecteur(id: number): Observable<StandSecteur> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<StandSecteur>(url);
  }

  deleteStandSecteur(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }
}
