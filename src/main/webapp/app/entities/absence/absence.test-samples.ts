import dayjs from 'dayjs/esm';

import { IAbsence, NewAbsence } from './absence.model';

export const sampleWithRequiredData: IAbsence = {
  id: 36911,
  dateabsence: dayjs('2023-04-13'),
};

export const sampleWithPartialData: IAbsence = {
  id: 96765,
  dateabsence: dayjs('2023-04-13'),
  justifie: false,
};

export const sampleWithFullData: IAbsence = {
  id: 88316,
  dateabsence: dayjs('2023-04-13'),
  justifie: false,
  commentaire: 'compress',
};

export const sampleWithNewData: NewAbsence = {
  dateabsence: dayjs('2023-04-12'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
