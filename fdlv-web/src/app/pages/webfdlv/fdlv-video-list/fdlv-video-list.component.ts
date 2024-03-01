import { Component, LOCALE_ID, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';

import { ConfirmationService, MessageService } from 'primeng/api';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { Table, TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { CardModule } from 'primeng/card';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { FdlvVideoService } from '../../../services/webfdlv/fdlv-video.service';
import { FdlvVideo } from '../../../model/webfdlv/fdlv-video.model';
import { ITheme } from '../../../model/theme.model';
import { ThemeService } from '../../../services/webfdlv/theme.service';
import { FdlvUserService } from '../../../services/webfdlv/fdlv-users.service';
import { DialogDeleteComponent } from '../../shared/dialog-delete/dialog-delete.component';
import { Router } from '@angular/router';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'fdlv-fdlv-video',
  templateUrl: './fdlv-video-list.component.html',
  styleUrls: ['./fdlv-video-list.component.scss'],
  standalone:true,
  providers:[
    { provide: LOCALE_ID, useValue: 'fr-FR' },
    MessageService,
    ConfirmationService,
  ],
  imports: [
    CommonModule,
    ButtonModule,
    TableModule,
    ToastModule,
    CardModule,
    ConfirmPopupModule,
    DialogDeleteComponent,FormsModule,
  
  ]
})
export class FdlvVideosComponent implements OnInit {
  fdlvVideos?: FdlvVideo[];
  themes?: ITheme[];
  isLoading = false;
  showConfirmationDialog: boolean = false;
  idToDelete: any;
  storedSearchValue = "";
  @ViewChild('idV', { static: true }) myTab: Table;
  constructor(
    protected fdlvVideoService: FdlvVideoService,
    protected themeService: ThemeService,
    protected userService: FdlvUserService,
    private router: Router ,
    private alertService: AlertService
  ) {}

  async loadAll(): Promise<void> {
    this.isLoading = true;   
    const themesRes: HttpResponse<ITheme[]> = await this.themeService.query().toPromise();
    this.themes = themesRes.body ?? [];
    const fdlvVideoRes: HttpResponse<FdlvVideo[]> = await this.fdlvVideoService.findAll().toPromise();
    this.fdlvVideos = fdlvVideoRes.body ?? [];
    const fdlvVideosFlvFusIds = this.fdlvVideos.map(fdlvVideo => fdlvVideo.flvFusId).filter(fusId => fusId !== undefined);
    const userLoginPromises = fdlvVideosFlvFusIds.map(fusId => this.userService.getUserLogin(<number>fusId));
    const userLogins = await Promise.all(userLoginPromises);
    this.fdlvVideos.forEach((fdlvVideo, index) => {
      fdlvVideo.userLogin = userLogins[index] ?? 'UNKNOWN';
    });
      this.isLoading = !this.isLoading;
  
  }

  ngOnInit(): void {
    this.loadAll();   
    this.storedSearchValue = localStorage.getItem("storedSearchValueV")? localStorage.getItem("storedSearchValueV") : "";
    if(this.storedSearchValue){
      this.myTab.filterGlobal(this.storedSearchValue, 'contains')
    }
  }

  onSearchChange(event: any){
    this.storedSearchValue = event.target.value;
    localStorage.setItem("storedSearchValueV", this.storedSearchValue.toString());
  }

  getThemeByVideoThemeId(id?: number): string {
    if (this.themes === undefined) {
      return 'Non défini';
    }
    return this.themes.filter(t => t.id === id)[0].label ?? 'Non défini';
  }

  trackId(index: number, item: FdlvVideo): number {
    return item.id!;
  }

  delete(id: number): void {
    this.fdlvVideoService.delete(id).subscribe({
      next:()=>{
        this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Média", detail:"La suppression est effectuée."})
        this.closeConfirmationDialog()
        this.loadAll()
      }, error:()=>  this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Média", detail:"Echec de suppression."})

    })
  }

  create(){
    this.router.navigateByUrl('fdlv-video-create');
  }
  
  edit(video: FdlvVideo){
    this.router.navigate(['fdlv-video-create'], { state: { data: video, type: 'edit' } });

  }
  openConfirmationDialog(id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }
  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }
  first = Number(localStorage.getItem("pageVideo"))
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageVideo", this.first.toString());
}
}
