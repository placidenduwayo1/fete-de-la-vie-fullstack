import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpResponse } from '@angular/common/http';
import { IStage } from 'src/app/model/stage.model';
import { Router, RouterModule } from '@angular/router';
import { AlertSeverity } from 'src/app/enums/alert-severity.enum';
import { AlertService } from 'src/app/shared/alert/alert.service';
import { StageService } from 'src/app/services/stage.service';
import { CardModule } from 'primeng/card';
import { DialogDeleteComponent } from '../../shared/dialog-delete/dialog-delete.component';
import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { SharedModule } from 'primeng/api';
import { ProgressSpinnerModule } from 'primeng/progressspinner';

@Component({
  selector: 'app-step-list',
  standalone: true,
  imports: [CommonModule,CardModule, DialogDeleteComponent, TableModule, ButtonModule, RouterModule,SharedModule, ProgressSpinnerModule],
  templateUrl: './step-list.component.html',
  styleUrls: ['./step-list.component.scss']
})
export class StageListComponent implements OnInit {
  stages?: IStage[];
  isLoading = false;
  showConfirmationDialog: boolean = false;
  idToDelete: any;
  constructor(public stageService: StageService, private router: Router, private alertService: AlertService) {}

  loadAll(): void {
    this.isLoading = true;

    this.stageService.query().subscribe( {
      next:(res: HttpResponse<IStage[]>) => {
        this.isLoading = false;
        this.stages = res.body ?? [];
        console.log("hello ", this.stages)
      },
      complete:() => {
        this.isLoading = false;
      }    
    }     
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IStage): number {
    return item.id!;
  }

  delete(id: any): void {
   
    this.stageService.delete(id).subscribe( {
      next:()=>{
        this.alertService.addMessage({severity:AlertSeverity.SUCCESS,summary:"Étapes", detail:"La suppression est effectuée."})
        this.closeConfirmationDialog()
      this.loadAll()
      },
      error:()=>  this.alertService.addMessage({severity:AlertSeverity.ERROR,summary:"Étapes", detail:"Echec de suppression."})

    })
    
  }
  edit(stage: any){
    this.router.navigate(['step-create'], { state: { data: stage, type: 'edit' } });
  }
  create(){
    this.router.navigateByUrl('step-create');
  }
  openConfirmationDialog(id: number) {
    this.idToDelete = id
    this.showConfirmationDialog = !this.showConfirmationDialog;
  }
  closeConfirmationDialog() {
    this.showConfirmationDialog = false;
  }

  viewQuizz(quizz){
    this.router.navigate(['quizz-view'], { state: { data: quizz.id, type: 'view' } });
  }

  viewEvent(event){
    this.router.navigate(['event-view'], { state: { data: event.id, type: 'view' } });
  }
  first = Number(localStorage.getItem("pageStep"));
  onPageChange(event: any) {
    this.first = event.first;
    localStorage.setItem("pageStep", this.first.toString());
}
}
