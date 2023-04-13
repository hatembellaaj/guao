import dayjs from 'dayjs/esm';

import { IInscrption, NewInscrption } from './inscrption.model';

export const sampleWithRequiredData: IInscrption = {
  id: 15609,
  dateinscription: dayjs('2023-04-13'),
};

export const sampleWithPartialData: IInscrption = {
  id: 68142,
  dateinscription: dayjs('2023-04-13'),
};

export const sampleWithFullData: IInscrption = {
  id: 77213,
  dateinscription: dayjs('2023-04-13'),
  commentaire: 'Peso Vermont Towels',
  tarifinscription: 89565,
};

export const sampleWithNewData: NewInscrption = {
  dateinscription: dayjs('2023-04-13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
