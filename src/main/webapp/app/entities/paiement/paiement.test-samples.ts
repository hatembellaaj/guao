import dayjs from 'dayjs/esm';

import { emodepaiement } from 'app/entities/enumerations/emodepaiement.model';

import { IPaiement, NewPaiement } from './paiement.model';

export const sampleWithRequiredData: IPaiement = {
  id: 69996,
  montant: 52151,
  datepaiement: dayjs('2023-04-13'),
};

export const sampleWithPartialData: IPaiement = {
  id: 4235,
  montant: 93107,
  numcheque: 'alarm',
  datepaiement: dayjs('2023-04-13'),
  numrecu: 'Loan Grenadines quantifying',
  idinscription: 'e-commerce',
};

export const sampleWithFullData: IPaiement = {
  id: 87840,
  montant: 5124,
  modepaiement: emodepaiement['CHEQUE'],
  numcheque: 'overriding',
  datepaiement: dayjs('2023-04-13'),
  numrecu: 'invoice',
  idinscription: 'Money Shirt',
};

export const sampleWithNewData: NewPaiement = {
  montant: 9059,
  datepaiement: dayjs('2023-04-13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
