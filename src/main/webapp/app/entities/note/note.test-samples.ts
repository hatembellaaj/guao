import { INote, NewNote } from './note.model';

export const sampleWithRequiredData: INote = {
  id: 79739,
  noteexamen: 43719,
};

export const sampleWithPartialData: INote = {
  id: 19247,
  noteexamen: 54365,
  remarque: 'Pants invoice Nigeria',
};

export const sampleWithFullData: INote = {
  id: 32964,
  noteexamen: 98643,
  remarque: 'Cordoba value-added Convertible',
};

export const sampleWithNewData: NewNote = {
  noteexamen: 81577,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
