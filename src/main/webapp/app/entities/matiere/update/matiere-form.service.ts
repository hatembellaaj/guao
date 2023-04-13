import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IMatiere, NewMatiere } from '../matiere.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMatiere for edit and NewMatiereFormGroupInput for create.
 */
type MatiereFormGroupInput = IMatiere | PartialWithRequiredKeyOf<NewMatiere>;

type MatiereFormDefaults = Pick<NewMatiere, 'id'>;

type MatiereFormGroupContent = {
  id: FormControl<IMatiere['id'] | NewMatiere['id']>;
  nommatiere: FormControl<IMatiere['nommatiere']>;
  coefficient: FormControl<IMatiere['coefficient']>;
  nombreheure: FormControl<IMatiere['nombreheure']>;
  nombreexamen: FormControl<IMatiere['nombreexamen']>;
};

export type MatiereFormGroup = FormGroup<MatiereFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MatiereFormService {
  createMatiereFormGroup(matiere: MatiereFormGroupInput = { id: null }): MatiereFormGroup {
    const matiereRawValue = {
      ...this.getFormDefaults(),
      ...matiere,
    };
    return new FormGroup<MatiereFormGroupContent>({
      id: new FormControl(
        { value: matiereRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      nommatiere: new FormControl(matiereRawValue.nommatiere, {
        validators: [Validators.required],
      }),
      coefficient: new FormControl(matiereRawValue.coefficient),
      nombreheure: new FormControl(matiereRawValue.nombreheure),
      nombreexamen: new FormControl(matiereRawValue.nombreexamen),
    });
  }

  getMatiere(form: MatiereFormGroup): IMatiere | NewMatiere {
    return form.getRawValue() as IMatiere | NewMatiere;
  }

  resetForm(form: MatiereFormGroup, matiere: MatiereFormGroupInput): void {
    const matiereRawValue = { ...this.getFormDefaults(), ...matiere };
    form.reset(
      {
        ...matiereRawValue,
        id: { value: matiereRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): MatiereFormDefaults {
    return {
      id: null,
    };
  }
}
