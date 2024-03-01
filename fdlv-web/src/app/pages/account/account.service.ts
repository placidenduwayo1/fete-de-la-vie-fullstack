import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApplicationConfigService } from 'src/app/core/config/application-config.service';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private Httpclient:HttpClient,private applicationConfigService:ApplicationConfigService) { }


  getAccount()
  {
    return this.Httpclient.get(this.applicationConfigService.getEndpointApiFor('/account'))
  }

updateAccount(account:any){
  return this.Httpclient.post(this.applicationConfigService.getEndpointApiFor('/account'),account)
}
}
