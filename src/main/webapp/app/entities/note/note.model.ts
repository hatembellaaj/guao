import { IEleve } from 'app/entities/eleve/eleve.model';
import { IExamen } from 'app/entities/examen/examen.model';

export interface INote {
  id: number;
  noteexamen?: number | null;
  remarque?: string | null;
  eleve?: IEleve | null;
  examen?: IExamen | null;
}

export type NewNote = Omit<INote, 'id'> & { id: null };
