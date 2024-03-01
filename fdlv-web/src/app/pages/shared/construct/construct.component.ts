/* eslint-disable @typescript-eslint/no-empty-function */
/* eslint-disable @angular-eslint/no-empty-lifecycle-method */
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'gmd-construct',
  templateUrl: './construct.component.html',
  styleUrls: ['./construct.component.scss'],
  standalone: true,
  imports:[
    CardModule
  ]
})
export class ConstructComponent implements OnInit {
  constructor(public router: Router) {}

  ngOnInit(): void {}
}
