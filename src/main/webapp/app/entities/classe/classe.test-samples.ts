import { IClasse, NewClasse } from './classe.model';

export const sampleWithRequiredData: IClasse = {
  id: 50071,
  nomclasse: 'Pakistan',
};

export const sampleWithPartialData: IClasse = {
  id: 84846,
  nomclasse: 'revolutionize',
};

export const sampleWithFullData: IClasse = {
  id: 65230,
  nomclasse: 'invoice Federation',
  nombreeleves: 95037,
};

export const sampleWithNewData: NewClasse = {
  nomclasse: 'Account',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
