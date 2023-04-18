import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ProfesseurFormService, ProfesseurFormGroup } from './professeur-form.service';
import { IProfesseur } from '../professeur.model';
import { ProfesseurService } from '../service/professeur.service';
import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

@Component({
  selector: 'jhi-professeur-update',
  templateUrl: './professeur-update.component.html',
})
export class ProfesseurUpdateComponent implements OnInit {
  isSaving = false;
  professeur: IProfesseur | null = null;

  usersSharedCollection: IUser[] = [];

  editForm: ProfesseurFormGroup = this.professeurFormService.createProfesseurFormGroup();

  constructor(
    protected professeurService: ProfesseurService,
    protected professeurFormService: ProfesseurFormService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareUser = (o1: IUser | null, o2: IUser | null): boolean => this.userService.compareUser(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ professeur }) => {
      this.professeur = professeur;
      if (professeur) {
        this.updateForm(professeur);
      }

      this.loadRelationshipsOptions();
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

    this.usersSharedCollection = this.userService.addUserToCollectionIfMissing<IUser>(this.usersSharedCollection, professeur.user);
  }

  protected loadRelationshipsOptions(): void {
    this.userService
      .query()
      .pipe(map((res: HttpResponse<IUser[]>) => res.body ?? []))
      .pipe(map((users: IUser[]) => this.userService.addUserToCollectionIfMissing<IUser>(users, this.professeur?.user)))
      .subscribe((users: IUser[]) => (this.usersSharedCollection = users));
  }
}
