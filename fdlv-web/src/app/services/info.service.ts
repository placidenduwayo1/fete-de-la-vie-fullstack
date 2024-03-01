import { Injectable } from "@angular/core";
import { MessageService } from "primeng/api";

enum Type {
    SUCCESS = 'success',
    INFO= 'info',
    DEFAULT='default',
    WARNING='warning',
    DANGER='danger'
}

@Injectable({
    providedIn: 'root',
  })
export class InfoService {

    constructor(private messageService: MessageService){}

    onSuccess(message: string){
        this.messageService.add({
            severity: Type.SUCCESS,
            summary: 'Acteur',
            detail: message,
          });
    }

}