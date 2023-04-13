import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { AbsenceFormService, AbsenceFormGroup } from './absence-form.service';
import { IAbsence } from '../absence.model';
import { AbsenceService } from '../service/absence.service';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { EleveService } from 'app/entities/eleve/service/eleve.service';

@Component({
  selector: 'jhi-absence-update',
  templateUrl: './absence-update.component.html',
})
export class AbsenceUpdateComponent implements OnInit {
  isSaving = false;
  absence: IAbsence | null = null;

  elevesSharedCollection: IEleve[] = [];

  editForm: AbsenceFormGroup = this.absenceFormService.createAbsenceFormGroup();

  constructor(
    protected absenceService: AbsenceService,
    protected absenceFormService: AbsenceFormService,
    protected eleveService: EleveService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEleve = (o1: IEleve | null, o2: IEleve | null): boolean => this.eleveService.compareEleve(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ absence }) => {
      this.absence = absence;
      if (absence) {
        this.updateForm(absence);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const absence = this.absenceFormService.getAbsence(this.editForm);
    if (absence.id !== null) {
      this.subscribeToSaveResponse(this.absenceService.update(absence));
    } else {
      this.subscribeToSaveResponse(this.absenceService.create(absence));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAbsence>>): void {
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

  protected updateForm(absence: IAbsence): void {
    this.absence = absence;
    this.absenceFormService.resetForm(this.editForm, absence);

    this.elevesSharedCollection = this.eleveService.addEleveToCollectionIfMissing<IEleve>(this.elevesSharedCollection, absence.eleve);
  }

  protected loadRelationshipsOptions(): void {
    this.eleveService
      .query()
      .pipe(map((res: HttpResponse<IEleve[]>) => res.body ?? []))
      .pipe(map((eleves: IEleve[]) => this.eleveService.addEleveToCollectionIfMissing<IEleve>(eleves, this.absence?.eleve)))
      .subscribe((eleves: IEleve[]) => (this.elevesSharedCollection = eleves));
  }
}
