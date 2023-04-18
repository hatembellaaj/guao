export interface IProfesseur {
  id: number;
  grade?: string | null;
  specialite?: string | null;
  typecontrat?: string | null;
  annecontrat?: string | null;
}

export type NewProfesseur = Omit<IProfesseur, 'id'> & { id: null };
