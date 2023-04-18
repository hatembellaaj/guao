import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProfesseur, NewProfesseur } from '../professeur.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProfesseur for edit and NewProfesseurFormGroupInput for create.
 */
type ProfesseurFormGroupInput = IProfesseur | PartialWithRequiredKeyOf<NewProfesseur>;

type ProfesseurFormDefaults = Pick<NewProfesseur, 'id'>;

type ProfesseurFormGroupContent = {
  id: FormControl<IProfesseur['id'] | NewProfesseur['id']>;
  grade: FormControl<IProfesseur['grade']>;
  specialite: FormControl<IProfesseur['specialite']>;
  typecontrat: FormControl<IProfesseur['typecontrat']>;
  annecontrat: FormControl<IProfesseur['annecontrat']>;
  user: FormControl<IProfesseur['user']>;
};

export type ProfesseurFormGroup = FormGroup<ProfesseurFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProfesseurFormService {
  createProfesseurFormGroup(professeur: ProfesseurFormGroupInput = { id: null }): ProfesseurFormGroup {
    const professeurRawValue = {
      ...this.getFormDefaults(),
      ...professeur,
    };
    return new FormGroup<ProfesseurFormGroupContent>({
      id: new FormControl(
        { value: professeurRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      grade: new FormControl(professeurRawValue.grade),
      specialite: new FormControl(professeurRawValue.specialite),
      typecontrat: new FormControl(professeurRawValue.typecontrat),
      annecontrat: new FormControl(professeurRawValue.annecontrat),
      user: new FormControl(professeurRawValue.user, {
        validators: [Validators.required],
      }),
    });
  }

  getProfesseur(form: ProfesseurFormGroup): IProfesseur | NewProfesseur {
    return form.getRawValue() as IProfesseur | NewProfesseur;
  }

  resetForm(form: ProfesseurFormGroup, professeur: ProfesseurFormGroupInput): void {
    const professeurRawValue = { ...this.getFormDefaults(), ...professeur };
    form.reset(
      {
        ...professeurRawValue,
        id: { value: professeurRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProfesseurFormDefaults {
    return {
      id: null,
    };
  }
}
