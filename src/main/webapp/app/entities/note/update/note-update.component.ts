import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { NoteFormService, NoteFormGroup } from './note-form.service';
import { INote } from '../note.model';
import { NoteService } from '../service/note.service';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { EleveService } from 'app/entities/eleve/service/eleve.service';
import { IExamen } from 'app/entities/examen/examen.model';
import { ExamenService } from 'app/entities/examen/service/examen.service';

@Component({
  selector: 'jhi-note-update',
  templateUrl: './note-update.component.html',
})
export class NoteUpdateComponent implements OnInit {
  isSaving = false;
  note: INote | null = null;

  elevesSharedCollection: IEleve[] = [];
  examenSharedCollection: IExamen[] = [];

  editForm: NoteFormGroup = this.noteFormService.createNoteFormGroup();

  constructor(
    protected noteService: NoteService,
    protected noteFormService: NoteFormService,
    protected eleveService: EleveService,
    protected examenService: ExamenService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEleve = (o1: IEleve | null, o2: IEleve | null): boolean => this.eleveService.compareEleve(o1, o2);

  compareExamen = (o1: IExamen | null, o2: IExamen | null): boolean => this.examenService.compareExamen(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ note }) => {
      this.note = note;
      if (note) {
        this.updateForm(note);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const note = this.noteFormService.getNote(this.editForm);
    if (note.id !== null) {
      this.subscribeToSaveResponse(this.noteService.update(note));
    } else {
      this.subscribeToSaveResponse(this.noteService.create(note));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INote>>): void {
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

  protected updateForm(note: INote): void {
    this.note = note;
    this.noteFormService.resetForm(this.editForm, note);

    this.elevesSharedCollection = this.eleveService.addEleveToCollectionIfMissing<IEleve>(this.elevesSharedCollection, note.eleve);
    this.examenSharedCollection = this.examenService.addExamenToCollectionIfMissing<IExamen>(this.examenSharedCollection, note.examen);
  }

  protected loadRelationshipsOptions(): void {
    this.eleveService
      .query()
      .pipe(map((res: HttpResponse<IEleve[]>) => res.body ?? []))
      .pipe(map((eleves: IEleve[]) => this.eleveService.addEleveToCollectionIfMissing<IEleve>(eleves, this.note?.eleve)))
      .subscribe((eleves: IEleve[]) => (this.elevesSharedCollection = eleves));

    this.examenService
      .query()
      .pipe(map((res: HttpResponse<IExamen[]>) => res.body ?? []))
      .pipe(map((examen: IExamen[]) => this.examenService.addExamenToCollectionIfMissing<IExamen>(examen, this.note?.examen)))
      .subscribe((examen: IExamen[]) => (this.examenSharedCollection = examen));
  }
}
