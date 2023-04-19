import dayjs from 'dayjs/esm';
import { IMatiere } from 'app/entities/matiere/matiere.model';
import { IClasse } from 'app/entities/classe/classe.model';

export interface IExamen {
  id: number;
  numexamen?: number | null;
  pourcentage?: number | null;
  valide?: boolean | null;
  date?: dayjs.Dayjs | null;
  duree?: number | null;
  matiere?: IMatiere | null;
  classe?: IClasse | null;
}

export type NewExamen = Omit<IExamen, 'id'> & { id: null };
