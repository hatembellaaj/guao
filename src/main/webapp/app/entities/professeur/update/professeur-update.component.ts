import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ProfesseurFormService, ProfesseurFormGroup } from './professeur-form.service';
import { IProfesseur } from '../professeur.model';
import { ProfesseurService } from '../service/professeur.service';

@Component({
  selector: 'jhi-professeur-update',
  templateUrl: './professeur-update.component.html',
})
export class ProfesseurUpdateComponent implements OnInit {
  isSaving = false;
  professeur: IProfesseur | null = null;

  editForm: ProfesseurFormGroup = this.professeurFormService.createProfesseurFormGroup();

  constructor(
    protected professeurService: ProfesseurService,
    protected professeurFormService: ProfesseurFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ professeur }) => {
      this.professeur = professeur;
      if (professeur) {
        this.updateForm(professeur);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const professeur = this.professeurFormService.getProfesseur(this.editForm);
    if (professeur.id !== null) {
      this.subscribeToSaveResponse(this.professeurService.update(professeur));
    } else {
      this.subscribeToSaveResponse(this.professeurService.create(professeur));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfesseur>>): void {
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

  protected updateForm(professeur: IProfesseur): void {
    this.professeur = professeur;
    this.professeurFormService.resetForm(this.editForm, professeur);
  }
}
