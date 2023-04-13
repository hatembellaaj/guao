import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAbsence, NewAbsence } from '../absence.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAbsence for edit and NewAbsenceFormGroupInput for create.
 */
type AbsenceFormGroupInput = IAbsence | PartialWithRequiredKeyOf<NewAbsence>;

type AbsenceFormDefaults = Pick<NewAbsence, 'id' | 'justifie'>;

type AbsenceFormGroupContent = {
  id: FormControl<IAbsence['id'] | NewAbsence['id']>;
  dateabsence: FormControl<IAbsence['dateabsence']>;
  justifie: FormControl<IAbsence['justifie']>;
  commentaire: FormControl<IAbsence['commentaire']>;
  eleve: FormControl<IAbsence['eleve']>;
};

export type AbsenceFormGroup = FormGroup<AbsenceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AbsenceFormService {
  createAbsenceFormGroup(absence: AbsenceFormGroupInput = { id: null }): AbsenceFormGroup {
    const absenceRawValue = {
      ...this.getFormDefaults(),
      ...absence,
    };
    return new FormGroup<AbsenceFormGroupContent>({
      id: new FormControl(
        { value: absenceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dateabsence: new FormControl(absenceRawValue.dateabsence, {
        validators: [Validators.required],
      }),
      justifie: new FormControl(absenceRawValue.justifie),
      commentaire: new FormControl(absenceRawValue.commentaire),
      eleve: new FormControl(absenceRawValue.eleve, {
        validators: [Validators.required],
      }),
    });
  }

  getAbsence(form: AbsenceFormGroup): IAbsence | NewAbsence {
    return form.getRawValue() as IAbsence | NewAbsence;
  }

  resetForm(form: AbsenceFormGroup, absence: AbsenceFormGroupInput): void {
    const absenceRawValue = { ...this.getFormDefaults(), ...absence };
    form.reset(
      {
        ...absenceRawValue,
        id: { value: absenceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): AbsenceFormDefaults {
    return {
      id: null,
      justifie: false,
    };
  }
}
