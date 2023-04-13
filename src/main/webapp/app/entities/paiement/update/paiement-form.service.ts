import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPaiement, NewPaiement } from '../paiement.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPaiement for edit and NewPaiementFormGroupInput for create.
 */
type PaiementFormGroupInput = IPaiement | PartialWithRequiredKeyOf<NewPaiement>;

type PaiementFormDefaults = Pick<NewPaiement, 'id'>;

type PaiementFormGroupContent = {
  id: FormControl<IPaiement['id'] | NewPaiement['id']>;
  montant: FormControl<IPaiement['montant']>;
  modepaiement: FormControl<IPaiement['modepaiement']>;
  numcheque: FormControl<IPaiement['numcheque']>;
  datepaiement: FormControl<IPaiement['datepaiement']>;
  numrecu: FormControl<IPaiement['numrecu']>;
  idinscription: FormControl<IPaiement['idinscription']>;
  eleve: FormControl<IPaiement['eleve']>;
};

export type PaiementFormGroup = FormGroup<PaiementFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PaiementFormService {
  createPaiementFormGroup(paiement: PaiementFormGroupInput = { id: null }): PaiementFormGroup {
    const paiementRawValue = {
      ...this.getFormDefaults(),
      ...paiement,
    };
    return new FormGroup<PaiementFormGroupContent>({
      id: new FormControl(
        { value: paiementRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      montant: new FormControl(paiementRawValue.montant, {
        validators: [Validators.required],
      }),
      modepaiement: new FormControl(paiementRawValue.modepaiement),
      numcheque: new FormControl(paiementRawValue.numcheque),
      datepaiement: new FormControl(paiementRawValue.datepaiement, {
        validators: [Validators.required],
      }),
      numrecu: new FormControl(paiementRawValue.numrecu),
      idinscription: new FormControl(paiementRawValue.idinscription),
      eleve: new FormControl(paiementRawValue.eleve, {
        validators: [Validators.required],
      }),
    });
  }

  getPaiement(form: PaiementFormGroup): IPaiement | NewPaiement {
    return form.getRawValue() as IPaiement | NewPaiement;
  }

  resetForm(form: PaiementFormGroup, paiement: PaiementFormGroupInput): void {
    const paiementRawValue = { ...this.getFormDefaults(), ...paiement };
    form.reset(
      {
        ...paiementRawValue,
        id: { value: paiementRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PaiementFormDefaults {
    return {
      id: null,
    };
  }
}
