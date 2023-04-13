import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEleve, NewEleve } from '../eleve.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEleve for edit and NewEleveFormGroupInput for create.
 */
type EleveFormGroupInput = IEleve | PartialWithRequiredKeyOf<NewEleve>;

type EleveFormDefaults = Pick<NewEleve, 'id'>;

type EleveFormGroupContent = {
  id: FormControl<IEleve['id'] | NewEleve['id']>;
  matricule: FormControl<IEleve['matricule']>;
  typedepaiement: FormControl<IEleve['typedepaiement']>;
  sexe: FormControl<IEleve['sexe']>;
  nom: FormControl<IEleve['nom']>;
  prenom: FormControl<IEleve['prenom']>;
  datedenaissance: FormControl<IEleve['datedenaissance']>;
  email: FormControl<IEleve['email']>;
  adresse: FormControl<IEleve['adresse']>;
  telephone: FormControl<IEleve['telephone']>;
  codepostale: FormControl<IEleve['codepostale']>;
  ville: FormControl<IEleve['ville']>;
  pays: FormControl<IEleve['pays']>;
  classe: FormControl<IEleve['classe']>;
};

export type EleveFormGroup = FormGroup<EleveFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EleveFormService {
  createEleveFormGroup(eleve: EleveFormGroupInput = { id: null }): EleveFormGroup {
    const eleveRawValue = {
      ...this.getFormDefaults(),
      ...eleve,
    };
    return new FormGroup<EleveFormGroupContent>({
      id: new FormControl(
        { value: eleveRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      matricule: new FormControl(eleveRawValue.matricule, {
        validators: [Validators.required],
      }),
      typedepaiement: new FormControl(eleveRawValue.typedepaiement),
      sexe: new FormControl(eleveRawValue.sexe),
      nom: new FormControl(eleveRawValue.nom, {
        validators: [Validators.required],
      }),
      prenom: new FormControl(eleveRawValue.prenom),
      datedenaissance: new FormControl(eleveRawValue.datedenaissance),
      email: new FormControl(eleveRawValue.email),
      adresse: new FormControl(eleveRawValue.adresse),
      telephone: new FormControl(eleveRawValue.telephone),
      codepostale: new FormControl(eleveRawValue.codepostale),
      ville: new FormControl(eleveRawValue.ville),
      pays: new FormControl(eleveRawValue.pays),
      classe: new FormControl(eleveRawValue.classe),
    });
  }

  getEleve(form: EleveFormGroup): IEleve | NewEleve {
    return form.getRawValue() as IEleve | NewEleve;
  }

  resetForm(form: EleveFormGroup, eleve: EleveFormGroupInput): void {
    const eleveRawValue = { ...this.getFormDefaults(), ...eleve };
    form.reset(
      {
        ...eleveRawValue,
        id: { value: eleveRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EleveFormDefaults {
    return {
      id: null,
    };
  }
}
