import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ExamenFormService, ExamenFormGroup } from './examen-form.service';
import { IExamen } from '../examen.model';
import { ExamenService } from '../service/examen.service';
import { IMatiere } from 'app/entities/matiere/matiere.model';
import { MatiereService } from 'app/entities/matiere/service/matiere.service';
import { IClasse } from 'app/entities/classe/classe.model';
import { ClasseService } from 'app/entities/classe/service/classe.service';

@Component({
  selector: 'jhi-examen-update',
  templateUrl: './examen-update.component.html',
})
export class ExamenUpdateComponent implements OnInit {
  isSaving = false;
  examen: IExamen | null = null;

  matieresSharedCollection: IMatiere[] = [];
  classesSharedCollection: IClasse[] = [];

  editForm: ExamenFormGroup = this.examenFormService.createExamenFormGroup();

  constructor(
    protected examenService: ExamenService,
    protected examenFormService: ExamenFormService,
    protected matiereService: MatiereService,
    protected classeService: ClasseService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareMatiere = (o1: IMatiere | null, o2: IMatiere | null): boolean => this.matiereService.compareMatiere(o1, o2);

  compareClasse = (o1: IClasse | null, o2: IClasse | null): boolean => this.classeService.compareClasse(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examen }) => {
      this.examen = examen;
      if (examen) {
        this.updateForm(examen);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const examen = this.examenFormService.getExamen(this.editForm);
    if (examen.id !== null) {
      this.subscribeToSaveResponse(this.examenService.update(examen));
    } else {
      this.subscribeToSaveResponse(this.examenService.create(examen));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamen>>): void {
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

  protected updateForm(examen: IExamen): void {
    this.examen = examen;
    this.examenFormService.resetForm(this.editForm, examen);

    this.matieresSharedCollection = this.matiereService.addMatiereToCollectionIfMissing<IMatiere>(
      this.matieresSharedCollection,
      examen.matiere
    );
    this.classesSharedCollection = this.classeService.addClasseToCollectionIfMissing<IClasse>(this.classesSharedCollection, examen.classe);
  }

  protected loadRelationshipsOptions(): void {
    this.matiereService
      .query()
      .pipe(map((res: HttpResponse<IMatiere[]>) => res.body ?? []))
      .pipe(map((matieres: IMatiere[]) => this.matiereService.addMatiereToCollectionIfMissing<IMatiere>(matieres, this.examen?.matiere)))
      .subscribe((matieres: IMatiere[]) => (this.matieresSharedCollection = matieres));

    this.classeService
      .query()
      .pipe(map((res: HttpResponse<IClasse[]>) => res.body ?? []))
      .pipe(map((classes: IClasse[]) => this.classeService.addClasseToCollectionIfMissing<IClasse>(classes, this.examen?.classe)))
      .subscribe((classes: IClasse[]) => (this.classesSharedCollection = classes));
  }
}
