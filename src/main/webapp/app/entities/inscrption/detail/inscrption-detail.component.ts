import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInscrption } from '../inscrption.model';

@Component({
  selector: 'jhi-inscrption-detail',
  templateUrl: './inscrption-detail.component.html',
})
export class InscrptionDetailComponent implements OnInit {
  inscrption: IInscrption | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inscrption }) => {
      this.inscrption = inscrption;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
