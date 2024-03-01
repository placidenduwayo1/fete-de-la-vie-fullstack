import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApplicationConfigService} from "../config/application-config.service";
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs/internal/Observable';
import { Storage } from 'src/app/enums/storage';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  helper=new JwtHelperService()

  constructor(private http: HttpClient,private applicationConfigService:ApplicationConfigService) { }

  login(user:any){

    return this.http.post(this.applicationConfigService.getEndpointApiFor('/authenticate'),user)

  }
  logout() {
      localStorage.removeItem(Storage.ID_TOKEN);
  }


  getToken() {

    return  localStorage.getItem(Storage.ID_TOKEN);

  }
  saveToken(id_token:any){

    localStorage.setItem(Storage.ID_TOKEN,id_token)

  }
  islogIn(){

    let token:any=localStorage.getItem(Storage.ID_TOKEN)

    if(!token ){
      return false
    }
    if(this.helper.isTokenExpired(token)){
      return false
    }
    return true
  }


  getIdentity(id_token:any){
return JSON.parse(atob(id_token.split('.')[1]))


  }
}

