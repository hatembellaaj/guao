import { IMatiere, NewMatiere } from './matiere.model';

export const sampleWithRequiredData: IMatiere = {
  id: 60871,
  nommatiere: 'Granite Bike Panama',
};

export const sampleWithPartialData: IMatiere = {
  id: 88197,
  nommatiere: 'frame Chicken generating',
  nombreheure: 4068,
};

export const sampleWithFullData: IMatiere = {
  id: 37193,
  nommatiere: 'content groupware process',
  coefficient: 36049,
  nombreheure: 51463,
  nombreexamen: 79961,
};

export const sampleWithNewData: NewMatiere = {
  nommatiere: 'help-desk',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
