//new modif debut
import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InterventionStructure } from 'src/app/model/forum/intervention-structure.model';
import { InterventionStructureService } from 'src/app/services/forum/intervention-structure.service';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { TableModule } from 'primeng/table';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { TooltipModule } from 'primeng/tooltip';
import { MessagesModule } from 'primeng/messages';


@Component({
    selector: 'app-intervention-structure',
    standalone: true,
    templateUrl: './intervention-structure.component.html',
    styleUrls: ['./intervention-structure.component.scss'],
    imports: [
      CommonModule,
      ConfirmDialogModule,
      TableModule,
      CardModule,
      ButtonModule,
      ToastModule,
      TooltipModule,
      MessagesModule
    ],
    providers: [MessageService, ConfirmationService]
})
export class InterventionStructureComponent{

  @Input () interventionsStructures: Array<InterventionStructure> = [];
  inputTextStyle1: string = `text-base text-color surface-overla p-2 border-1 border-solid 
  surface-border border-round appearance-none outline-none focus:border-primary w-full`;

  @Input () interventionStruct: Array<InterventionStructure>;
}
//new modif fin

