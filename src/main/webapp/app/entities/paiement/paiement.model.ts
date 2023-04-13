import dayjs from 'dayjs/esm';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { emodepaiement } from 'app/entities/enumerations/emodepaiement.model';

export interface IPaiement {
  id: number;
  montant?: number | null;
  modepaiement?: emodepaiement | null;
  numcheque?: string | null;
  datepaiement?: dayjs.Dayjs | null;
  numrecu?: string | null;
  idinscription?: string | null;
  eleve?: Pick<IEleve, 'id'> | null;
}

export type NewPaiement = Omit<IPaiement, 'id'> & { id: null };
