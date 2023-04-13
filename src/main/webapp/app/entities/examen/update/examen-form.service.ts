import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IExamen, NewExamen } from '../examen.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IExamen for edit and NewExamenFormGroupInput for create.
 */
type ExamenFormGroupInput = IExamen | PartialWithRequiredKeyOf<NewExamen>;

type ExamenFormDefaults = Pick<NewExamen, 'id' | 'valide'>;

type ExamenFormGroupContent = {
  id: FormControl<IExamen['id'] | NewExamen['id']>;
  numexamen: FormControl<IExamen['numexamen']>;
  pourcentage: FormControl<IExamen['pourcentage']>;
  valide: FormControl<IExamen['valide']>;
  date: FormControl<IExamen['date']>;
  duree: FormControl<IExamen['duree']>;
  matiere: FormControl<IExamen['matiere']>;
  classe: FormControl<IExamen['classe']>;
};

export type ExamenFormGroup = FormGroup<ExamenFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ExamenFormService {
  createExamenFormGroup(examen: ExamenFormGroupInput = { id: null }): ExamenFormGroup {
    const examenRawValue = {
      ...this.getFormDefaults(),
      ...examen,
    };
    return new FormGroup<ExamenFormGroupContent>({
      id: new FormControl(
        { value: examenRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      numexamen: new FormControl(examenRawValue.numexamen, {
        validators: [Validators.required],
      }),
      pourcentage: new FormControl(examenRawValue.pourcentage),
      valide: new FormControl(examenRawValue.valide),
      date: new FormControl(examenRawValue.date),
      duree: new FormControl(examenRawValue.duree),
      matiere: new FormControl(examenRawValue.matiere, {
        validators: [Validators.required],
      }),
      classe: new FormControl(examenRawValue.classe, {
        validators: [Validators.required],
      }),
    });
  }

  getExamen(form: ExamenFormGroup): IExamen | NewExamen {
    return form.getRawValue() as IExamen | NewExamen;
  }

  resetForm(form: ExamenFormGroup, examen: ExamenFormGroupInput): void {
    const examenRawValue = { ...this.getFormDefaults(), ...examen };
    form.reset(
      {
        ...examenRawValue,
        id: { value: examenRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ExamenFormDefaults {
    return {
      id: null,
      valide: false,
    };
  }
}
