import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpResponse } from '@angular/common/http';
import { IEvent } from 'src/app/model/event.model';
import { FdlvUsers } from 'src/app/model/fdlv-user.model';
import { EventService } from 'src/app/services/event.service';
import { FdlvUserService } from 'src/app/services/webfdlv/fdlv-users.service';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { Router } from '@angular/router';
import { DialogDeleteComponent } from '../../shared/dialog-delete/dialog-delete.component';
import { TableModule } from 'primeng/table';
import { ProgressSpinnerModule } from 'primeng/progressspinner';

@Component({
  selector: 'app-event-list',
  standalone: true,
  imports: [CommonModule, CardModule, ButtonModule, DialogDeleteComponent, TableModule, ProgressSpinnerModule],
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.scss']
})
export class EventListComponent implements OnInit {
  events?: IEvent[];
  names = new Map<number | undefined, string>();
  isLoading = false;
  showConfirmationDialog: boolean = false;
  idToDelete: any
  constructor(protected eventService: EventService, protected userService: FdlvUserService, protected alertService: AlertService,
    private router: Router) {}

  loadAll(): void {
    this.isLoading = true;

    this.eventService.queryDetails().subscribe({
      next: (res: HttpResponse<IEvent[]>) => {
        this.events = res.body ?? [];
        this.events.forEach(event => {
          if (event.evtFcoFusId) {
            this.userService.find(event.evtFcoFusId).subscribe((resUser: HttpResponse<FdlvUsers>) => {
              if (resUser.body?.login) {
                this.names.set(event.id, resUser.body.login);
              }
            });
          }
        });
        this.isLoading = false;
      },complete: () => {
        this.isLoading = false;
      }
    }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IEvent): number {
    return item.id!;
  }

  delete(id: any): void {
   this.eventService.delete(id).subscribe( {
      next:()=>{
        this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Parcours", detail:"La suppression est effectuée."})
        this.closeConfirmationDialog()
      this.loadAll()
      },
      error:()=>  this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Parcours", detail:"Echec de suppression."})

    })
    
  }
  edit(event: any){
    this.router.navigate(['event-create'], { state: { data: event.id, type: 'edit' } });
  }
  create(){
    this.router.navigateByUrl('event-create');
  }
  
  openConfirmationDialog(id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }
  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }

  viewEvent(event){
    this.router.navigate(['event-view'], { state: { data: event.id, type: 'view' } });
  }

  first = Number(localStorage.getItem("pageEvent"));
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageEvent", this.first.toString());
}
}
