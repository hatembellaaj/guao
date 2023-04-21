import dayjs from 'dayjs/esm';
import { IClasse } from 'app/entities/classe/classe.model';

export interface IInscrption {
  id: number;
  dateinscription?: dayjs.Dayjs | null;
  commentaire?: string | null;
  tarifinscription?: number | null;
  classe?: IClasse | null;
}

export type NewInscrption = Omit<IInscrption, 'id'> & { id: null };
