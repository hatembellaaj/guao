import dayjs from 'dayjs/esm';
import { IEleve } from 'app/entities/eleve/eleve.model';

export interface IAbsence {
  id: number;
  dateabsence?: dayjs.Dayjs | null;
  justifie?: boolean | null;
  commentaire?: string | null;
  eleve?: Pick<IEleve, 'id'> | null;
}

export type NewAbsence = Omit<IAbsence, 'id'> & { id: null };
