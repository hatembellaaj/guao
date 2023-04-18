import { IUser } from 'app/entities/user/user.model';

export interface IProfesseur {
  id: number;
  grade?: string | null;
  specialite?: string | null;
  typecontrat?: string | null;
  annecontrat?: string | null;
  user?: Pick<IUser, 'id' | 'login'> | null;
}

export type NewProfesseur = Omit<IProfesseur, 'id'> & { id: null };
