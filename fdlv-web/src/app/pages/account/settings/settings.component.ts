import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule} from "@angular/forms";
import {CardModule} from "primeng/card";
import {InputTextModule} from "primeng/inputtext";
import {ButtonModule} from "primeng/button";
import { AccountService } from '../account.service';
import { Account } from 'src/app/core/auth/account.model';
import { first } from 'rxjs';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [CommonModule, FormsModule, CardModule, InputTextModule, ButtonModule],
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit{

  Account:Account = { activated: true,
    email:  '',
    firstName:  '',
    langKey:  '',
    lastName:  '', 
    login:  '',
    imageUrl:  '',
  authorities:[] } ;
  constructor(private accountService:AccountService,private alertService:AlertService) {
  }

  ngOnInit() {
   this.accountService.getAccount().pipe(first()).subscribe((response:Account)=>{

     this.Account=response
     console.log(this.Account)
   })
  }

  updateAccount(){
    console.log(this.Account);
    this.accountService.updateAccount( this.Account).pipe(first()).subscribe({
      next:(account:Account)=>{
        this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Compte", detail:"Modifications sauvegardées."})
        console.log(account);
      },
      error:(err)=>{
        this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Compte", detail:"Echec de la sauvegarde."})
      }
    });

  }
}


