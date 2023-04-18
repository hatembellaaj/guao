import { IProfesseur, NewProfesseur } from './professeur.model';

export const sampleWithRequiredData: IProfesseur = {
  id: 57826,
};

export const sampleWithPartialData: IProfesseur = {
  id: 20140,
  specialite: 'Islands Executive Steel',
};

export const sampleWithFullData: IProfesseur = {
  id: 7799,
  grade: 'disintermediate',
  specialite: 'bypass',
  typecontrat: 'Principal',
  annecontrat: 'groupware website Sausages',
};

export const sampleWithNewData: NewProfesseur = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
