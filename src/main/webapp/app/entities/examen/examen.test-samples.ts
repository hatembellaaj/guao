import dayjs from 'dayjs/esm';

import { IExamen, NewExamen } from './examen.model';

export const sampleWithRequiredData: IExamen = {
  id: 47763,
  numexamen: 3844,
};

export const sampleWithPartialData: IExamen = {
  id: 71119,
  numexamen: 49109,
  valide: false,
};

export const sampleWithFullData: IExamen = {
  id: 84279,
  numexamen: 37755,
  pourcentage: 70003,
  valide: true,
  date: dayjs('2023-04-13'),
  duree: 23756,
};

export const sampleWithNewData: NewExamen = {
  numexamen: 54761,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
