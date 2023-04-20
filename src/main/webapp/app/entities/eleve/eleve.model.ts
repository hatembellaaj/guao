import dayjs from 'dayjs/esm';
import { IClasse } from 'app/entities/classe/classe.model';
import { etypedepaiement } from 'app/entities/enumerations/etypedepaiement.model';
import { esexe } from 'app/entities/enumerations/esexe.model';

export interface IEleve {
  id: number;
  matricule?: string | null;
  typedepaiement?: etypedepaiement | null;
  sexe?: esexe | null;
  nom?: string | null;
  prenom?: string | null;
  datedenaissance?: dayjs.Dayjs | null;
  email?: string | null;
  adresse?: string | null;
  telephone?: string | null;
  codepostale?: string | null;
  ville?: string | null;
  pays?: string | null;
  classe?: IClasse | null;
}

export type NewEleve = Omit<IEleve, 'id'> & { id: null };
