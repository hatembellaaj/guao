import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInscrption, NewInscrption } from '../inscrption.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInscrption for edit and NewInscrptionFormGroupInput for create.
 */
type InscrptionFormGroupInput = IInscrption | PartialWithRequiredKeyOf<NewInscrption>;

type InscrptionFormDefaults = Pick<NewInscrption, 'id'>;

type InscrptionFormGroupContent = {
  id: FormControl<IInscrption['id'] | NewInscrption['id']>;
  dateinscription: FormControl<IInscrption['dateinscription']>;
  commentaire: FormControl<IInscrption['commentaire']>;
  tarifinscription: FormControl<IInscrption['tarifinscription']>;
  classe: FormControl<IInscrption['classe']>;
};

export type InscrptionFormGroup = FormGroup<InscrptionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InscrptionFormService {
  createInscrptionFormGroup(inscrption: InscrptionFormGroupInput = { id: null }): InscrptionFormGroup {
    const inscrptionRawValue = {
      ...this.getFormDefaults(),
      ...inscrption,
    };
    return new FormGroup<InscrptionFormGroupContent>({
      id: new FormControl(
        { value: inscrptionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      dateinscription: new FormControl(inscrptionRawValue.dateinscription, {
        validators: [Validators.required],
      }),
      commentaire: new FormControl(inscrptionRawValue.commentaire),
      tarifinscription: new FormControl(inscrptionRawValue.tarifinscription),
      classe: new FormControl(inscrptionRawValue.classe, {
        validators: [Validators.required],
      }),
    });
  }

  getInscrption(form: InscrptionFormGroup): IInscrption | NewInscrption {
    return form.getRawValue() as IInscrption | NewInscrption;
  }

  resetForm(form: InscrptionFormGroup, inscrption: InscrptionFormGroupInput): void {
    const inscrptionRawValue = { ...this.getFormDefaults(), ...inscrption };
    form.reset(
      {
        ...inscrptionRawValue,
        id: { value: inscrptionRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): InscrptionFormDefaults {
    return {
      id: null,
    };
  }
}
