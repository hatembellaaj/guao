import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { INote, NewNote } from '../note.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts INote for edit and NewNoteFormGroupInput for create.
 */
type NoteFormGroupInput = INote | PartialWithRequiredKeyOf<NewNote>;

type NoteFormDefaults = Pick<NewNote, 'id'>;

type NoteFormGroupContent = {
  id: FormControl<INote['id'] | NewNote['id']>;
  noteexamen: FormControl<INote['noteexamen']>;
  remarque: FormControl<INote['remarque']>;
  eleve: FormControl<INote['eleve']>;
  examen: FormControl<INote['examen']>;
};

export type NoteFormGroup = FormGroup<NoteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class NoteFormService {
  createNoteFormGroup(note: NoteFormGroupInput = { id: null }): NoteFormGroup {
    const noteRawValue = {
      ...this.getFormDefaults(),
      ...note,
    };
    return new FormGroup<NoteFormGroupContent>({
      id: new FormControl(
        { value: noteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      noteexamen: new FormControl(noteRawValue.noteexamen, {
        validators: [Validators.required],
      }),
      remarque: new FormControl(noteRawValue.remarque),
      eleve: new FormControl(noteRawValue.eleve, {
        validators: [Validators.required],
      }),
      examen: new FormControl(noteRawValue.examen, {
        validators: [Validators.required],
      }),
    });
  }

  getNote(form: NoteFormGroup): INote | NewNote {
    return form.getRawValue() as INote | NewNote;
  }

  resetForm(form: NoteFormGroup, note: NoteFormGroupInput): void {
    const noteRawValue = { ...this.getFormDefaults(), ...note };
    form.reset(
      {
        ...noteRawValue,
        id: { value: noteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): NoteFormDefaults {
    return {
      id: null,
    };
  }
}
