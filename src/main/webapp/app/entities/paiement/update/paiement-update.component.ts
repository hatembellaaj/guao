import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PaiementFormService, PaiementFormGroup } from './paiement-form.service';
import { IPaiement } from '../paiement.model';
import { PaiementService } from '../service/paiement.service';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { EleveService } from 'app/entities/eleve/service/eleve.service';
import { emodepaiement } from 'app/entities/enumerations/emodepaiement.model';

@Component({
  selector: 'jhi-paiement-update',
  templateUrl: './paiement-update.component.html',
})
export class PaiementUpdateComponent implements OnInit {
  isSaving = false;
  paiement: IPaiement | null = null;
  emodepaiementValues = Object.keys(emodepaiement);

  elevesSharedCollection: IEleve[] = [];

  editForm: PaiementFormGroup = this.paiementFormService.createPaiementFormGroup();

  constructor(
    protected paiementService: PaiementService,
    protected paiementFormService: PaiementFormService,
    protected eleveService: EleveService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEleve = (o1: IEleve | null, o2: IEleve | null): boolean => this.eleveService.compareEleve(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ paiement }) => {
      this.paiement = paiement;
      if (paiement) {
        this.updateForm(paiement);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const paiement = this.paiementFormService.getPaiement(this.editForm);
    if (paiement.id !== null) {
      this.subscribeToSaveResponse(this.paiementService.update(paiement));
    } else {
      this.subscribeToSaveResponse(this.paiementService.create(paiement));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPaiement>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(paiement: IPaiement): void {
    this.paiement = paiement;
    this.paiementFormService.resetForm(this.editForm, paiement);

    this.elevesSharedCollection = this.eleveService.addEleveToCollectionIfMissing<IEleve>(this.elevesSharedCollection, paiement.eleve);
  }

  protected loadRelationshipsOptions(): void {
    this.eleveService
      .query()
      .pipe(map((res: HttpResponse<IEleve[]>) => res.body ?? []))
      .pipe(map((eleves: IEleve[]) => this.eleveService.addEleveToCollectionIfMissing<IEleve>(eleves, this.paiement?.eleve)))
      .subscribe((eleves: IEleve[]) => (this.elevesSharedCollection = eleves));
  }
}
