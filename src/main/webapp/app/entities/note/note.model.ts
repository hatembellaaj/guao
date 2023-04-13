import { IEleve } from 'app/entities/eleve/eleve.model';
import { IExamen } from 'app/entities/examen/examen.model';

export interface INote {
  id: number;
  noteexamen?: number | null;
  remarque?: string | null;
  eleve?: Pick<IEleve, 'id'> | null;
  examen?: Pick<IExamen, 'id'> | null;
}

export type NewNote = Omit<INote, 'id'> & { id: null };
