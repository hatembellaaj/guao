import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { MatiereFormService, MatiereFormGroup } from './matiere-form.service';
import { IMatiere } from '../matiere.model';
import { MatiereService } from '../service/matiere.service';

@Component({
  selector: 'jhi-matiere-update',
  templateUrl: './matiere-update.component.html',
})
export class MatiereUpdateComponent implements OnInit {
  isSaving = false;
  matiere: IMatiere | null = null;

  editForm: MatiereFormGroup = this.matiereFormService.createMatiereFormGroup();

  constructor(
    protected matiereService: MatiereService,
    protected matiereFormService: MatiereFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ matiere }) => {
      this.matiere = matiere;
      if (matiere) {
        this.updateForm(matiere);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const matiere = this.matiereFormService.getMatiere(this.editForm);
    if (matiere.id !== null) {
      this.subscribeToSaveResponse(this.matiereService.update(matiere));
    } else {
      this.subscribeToSaveResponse(this.matiereService.create(matiere));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatiere>>): void {
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

  protected updateForm(matiere: IMatiere): void {
    this.matiere = matiere;
    this.matiereFormService.resetForm(this.editForm, matiere);
  }
}
