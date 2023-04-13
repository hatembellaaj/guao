import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EleveFormService, EleveFormGroup } from './eleve-form.service';
import { IEleve } from '../eleve.model';
import { EleveService } from '../service/eleve.service';
import { IClasse } from 'app/entities/classe/classe.model';
import { ClasseService } from 'app/entities/classe/service/classe.service';
import { etypedepaiement } from 'app/entities/enumerations/etypedepaiement.model';
import { esexe } from 'app/entities/enumerations/esexe.model';

@Component({
  selector: 'jhi-eleve-update',
  templateUrl: './eleve-update.component.html',
})
export class EleveUpdateComponent implements OnInit {
  isSaving = false;
  eleve: IEleve | null = null;
  etypedepaiementValues = Object.keys(etypedepaiement);
  esexeValues = Object.keys(esexe);

  classesSharedCollection: IClasse[] = [];

  editForm: EleveFormGroup = this.eleveFormService.createEleveFormGroup();

  constructor(
    protected eleveService: EleveService,
    protected eleveFormService: EleveFormService,
    protected classeService: ClasseService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareClasse = (o1: IClasse | null, o2: IClasse | null): boolean => this.classeService.compareClasse(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eleve }) => {
      this.eleve = eleve;
      if (eleve) {
        this.updateForm(eleve);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eleve = this.eleveFormService.getEleve(this.editForm);
    if (eleve.id !== null) {
      this.subscribeToSaveResponse(this.eleveService.update(eleve));
    } else {
      this.subscribeToSaveResponse(this.eleveService.create(eleve));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEleve>>): void {
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

  protected updateForm(eleve: IEleve): void {
    this.eleve = eleve;
    this.eleveFormService.resetForm(this.editForm, eleve);

    this.classesSharedCollection = this.classeService.addClasseToCollectionIfMissing<IClasse>(this.classesSharedCollection, eleve.classe);
  }

  protected loadRelationshipsOptions(): void {
    this.classeService
      .query()
      .pipe(map((res: HttpResponse<IClasse[]>) => res.body ?? []))
      .pipe(map((classes: IClasse[]) => this.classeService.addClasseToCollectionIfMissing<IClasse>(classes, this.eleve?.classe)))
      .subscribe((classes: IClasse[]) => (this.classesSharedCollection = classes));
  }
}
