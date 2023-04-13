import dayjs from 'dayjs/esm';

import { etypedepaiement } from 'app/entities/enumerations/etypedepaiement.model';
import { esexe } from 'app/entities/enumerations/esexe.model';

import { IEleve, NewEleve } from './eleve.model';

export const sampleWithRequiredData: IEleve = {
  id: 8062,
  matricule: 'Cove',
  nom: 'experiences Concrete programming',
};

export const sampleWithPartialData: IEleve = {
  id: 61106,
  matricule: 'Handcrafted exploit',
  sexe: esexe['FEMME'],
  nom: 'Divide invoice rich',
  ville: 'up backing payment',
  pays: 'payment circuit',
};

export const sampleWithFullData: IEleve = {
  id: 64377,
  matricule: 'Infrastructure 1080p',
  typedepaiement: etypedepaiement['TRAITE'],
  sexe: esexe['HOMME'],
  nom: 'synergies Bosnia Director',
  prenom: 'Berkshire Borders teal',
  datedenaissance: dayjs('2023-04-13'),
  email: 'Chaim_Zemlak@yahoo.com',
  adresse: 'Chief',
  telephone: '(391) 928-8953',
  codepostale: 'black',
  ville: 'communities',
  pays: 'payment bandwidth plum',
};

export const sampleWithNewData: NewEleve = {
  matricule: 'real-time Auto Fresh',
  nom: 'Supervisor',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
