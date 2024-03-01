import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { ResolveFn } from "@angular/router";
import { Observable } from "rxjs";
import { ApplicationConfigService } from "src/app/core/config/application-config.service";
import { NiveauResponsabilite } from "src/app/model/forum/niveau-responsabilite.model";

@Injectable({providedIn:'root'})
export class NiveauResponsabiliteService {
   
    constructor(private applicationConfigService: ApplicationConfigService, private htpClient: HttpClient){}
    apiUrl: string = this.applicationConfigService.getEndpointApiFor('/niveauResponsabilites');

    getAllResponsibilitiesLevel(): Observable<NiveauResponsabilite[]> {
        return this.htpClient.get<NiveauResponsabilite[]>(this.apiUrl);
    }
    getNiveauResponsabiliteByLevel(level: number): Observable<NiveauResponsabilite[]>{
        const url = `${this.apiUrl}/${level}`
        return this.htpClient.get<NiveauResponsabilite[]>(url);
    }
}

export const GetAllResponsibilitiesLevelResolve: ResolveFn<NiveauResponsabilite[]> = ()=>{
    return inject(NiveauResponsabiliteService).getAllResponsibilitiesLevel();
}