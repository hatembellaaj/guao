import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { InscrptionFormService, InscrptionFormGroup } from './inscrption-form.service';
import { IInscrption } from '../inscrption.model';
import { InscrptionService } from '../service/inscrption.service';
import { IClasse } from 'app/entities/classe/classe.model';
import { ClasseService } from 'app/entities/classe/service/classe.service';

@Component({
  selector: 'jhi-inscrption-update',
  templateUrl: './inscrption-update.component.html',
})
export class InscrptionUpdateComponent implements OnInit {
  isSaving = false;
  inscrption: IInscrption | null = null;

  classesSharedCollection: IClasse[] = [];

  editForm: InscrptionFormGroup = this.inscrptionFormService.createInscrptionFormGroup();

  constructor(
    protected inscrptionService: InscrptionService,
    protected inscrptionFormService: InscrptionFormService,
    protected classeService: ClasseService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareClasse = (o1: IClasse | null, o2: IClasse | null): boolean => this.classeService.compareClasse(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ inscrption }) => {
      this.inscrption = inscrption;
      if (inscrption) {
        this.updateForm(inscrption);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const inscrption = this.inscrptionFormService.getInscrption(this.editForm);
    if (inscrption.id !== null) {
      this.subscribeToSaveResponse(this.inscrptionService.update(inscrption));
    } else {
      this.subscribeToSaveResponse(this.inscrptionService.create(inscrption));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInscrption>>): void {
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

  protected updateForm(inscrption: IInscrption): void {
    this.inscrption = inscrption;
    this.inscrptionFormService.resetForm(this.editForm, inscrption);

    this.classesSharedCollection = this.classeService.addClasseToCollectionIfMissing<IClasse>(
      this.classesSharedCollection,
      inscrption.classe
    );
  }

  protected loadRelationshipsOptions(): void {
    this.classeService
      .query()
      .pipe(map((res: HttpResponse<IClasse[]>) => res.body ?? []))
      .pipe(map((classes: IClasse[]) => this.classeService.addClasseToCollectionIfMissing<IClasse>(classes, this.inscrption?.classe)))
      .subscribe((classes: IClasse[]) => (this.classesSharedCollection = classes));
  }
}
