import {Component, OnInit, WritableSignal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActivatedRoute} from "@angular/router";
import {TableModule} from "primeng/table";
import {StoreService} from "../../services/store.service";
import {ItemFamilyEnum} from "../../enums/item-family.enum";
import {Theme} from "../../model/theme.model";

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [
    CommonModule,
    TableModule
  ],
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss']
})
export class TableComponent<T> implements OnInit{
  public columnData!: any;
  public data!: WritableSignal<T[]>;

  constructor(private _route: ActivatedRoute, private storeService: StoreService<T>) {}
  ngOnInit(): void {
    this._route.data.subscribe(data => {
      this.columnData = data['columnData'];
      this.data = this.storeService.getAll(ItemFamilyEnum.GOMYDEFI, 'events') as WritableSignal<T[]>;
    });
  }

  reset() {
    this.data.set([])
  }

}
