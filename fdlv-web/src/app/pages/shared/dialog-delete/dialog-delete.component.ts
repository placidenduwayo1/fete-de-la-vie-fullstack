import { Component, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'primeng/api';
import { TableModule } from 'primeng/table';
import { MessageModule } from 'primeng/message';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { DialogModule } from 'primeng/dialog';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-dialog-delete',
  standalone: true,
  imports: [CommonModule,
    SharedModule,
    TableModule,
    ButtonModule,
    ToastModule,
    MessageModule,
    ConfirmPopupModule,
    DialogModule,],
  templateUrl: './dialog-delete.component.html',
  styleUrls: ['./dialog-delete.component.scss']
})
export class DialogDeleteComponent implements OnChanges{
    
  @Input() showConfirmationDialog: boolean = false
  @Input() elementLabel: string ;
  @Output() delete : EventEmitter<any> = new EventEmitter();
  @Input() elementToBeDeleted: any ;
  @Output() closeConfirmationDialog: EventEmitter<boolean> = new EventEmitter();


  ngOnChanges(changes: SimpleChanges): void {    
    this.showConfirmationDialog = changes['showConfirmationDialog'].currentValue; 
  }
  close() {
    this.closeConfirmationDialog.emit();
  }
  deleteEl(){
    this.delete.emit(this.elementToBeDeleted);

  }


}
