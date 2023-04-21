import dayjs from 'dayjs/esm';
import { IEleve } from 'app/entities/eleve/eleve.model';

export interface IAbsence {
  id: number;
  dateabsence?: dayjs.Dayjs | null;
  justifie?: boolean | null;
  commentaire?: string | null;
  eleve?: IEleve | null;
}

export type NewAbsence = Omit<IAbsence, 'id'> & { id: null };
