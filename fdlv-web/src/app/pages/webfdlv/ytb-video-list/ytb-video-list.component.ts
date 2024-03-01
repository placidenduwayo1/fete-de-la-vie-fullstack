import { Component, OnInit , LOCALE_ID, ViewChild} from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ConfirmationService, MessageService, SharedModule } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { Table, TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
import { ToastModule } from 'primeng/toast';
import { MessageModule } from 'primeng/message';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { DialogModule } from 'primeng/dialog';
import { CardModule } from 'primeng/card';
import { YtbVideoService } from '../../../services/webfdlv/ytb-video.service';
import { IYtbVideo } from '../../../model/webfdlv/ytb-video.model';
import { DialogDeleteComponent } from '../../shared/dialog-delete/dialog-delete.component';
import { Router } from '@angular/router';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'fdlv-ytb-video',
  templateUrl: './ytb-video-list.component.html',
  styleUrls: ['./ytb-video-list.component.scss'],
  standalone: true,
  providers: [
    { provide: LOCALE_ID, useValue: 'fr-FR' },
    MessageService,
    ConfirmationService,
  ],
  imports: [SharedModule, 
    CommonModule,
    FormsModule,
     TableModule,
     ButtonModule,
     ToastModule,
     MessageModule,
     ConfirmPopupModule,
     DialogModule, 
     CardModule,
     DialogDeleteComponent,
     
   ],
})
export class YtbVideosComponent implements OnInit {
  ytbVideos?: IYtbVideo[];
  isLoading = false;
  showConfirmationDialog: boolean = false ;
  idToDelete: any ;
  
  storedSearchValue = "";
  @ViewChild('idY', { static: true }) myTab: Table;
  constructor(protected ytbVideoService: YtbVideoService,
    private router: Router,
    private alertService: AlertService
    ) {}

  loadAll(): void {
    this.isLoading = true;

    this.ytbVideoService.query()
     .subscribe({
      next: (res: HttpResponse<IYtbVideo[]>) =>{

        this.isLoading = false;
        this.ytbVideos = res.body ?? [];
      },
      error: () => [],
      complete: () => {
        this.isLoading = false;
      }
    });

   
  }

  ngOnInit(): void {
    this.loadAll();
    this.storedSearchValue = localStorage.getItem("storedSearchValueY")? localStorage.getItem("storedSearchValueY") : "";
    if(this.storedSearchValue){
      this.myTab.filterGlobal(this.storedSearchValue, 'contains')
    }
  }

  onSearchChange(event: any){
    this.storedSearchValue = event.target.value;
    localStorage.setItem("storedSearchValueY", this.storedSearchValue.toString());
  }

  delete(id: number): void {
    this.ytbVideoService.delete(id).subscribe( {
      next:()=>{
        this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Video", detail:"La suppression est effectuée."})
        this.closeConfirmationDialog()
        this.loadAll()
      },
      error:()=>  this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Video", detail:"Echec de suppression."})

    })
    
    this.showConfirmationDialog = false;
  }
  create(){
    this.router.navigateByUrl('ytb-video-create');
  }
  
  edit(video: IYtbVideo){
    this.router.navigate(['ytb-video-create'], { state: { data: video, type: 'edit' } });

  }
  openConfirmationDialog(id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }
  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }

  first = Number(localStorage.getItem("pageYtbVideo"))
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageYtbVideo", this.first.toString());
}
}
