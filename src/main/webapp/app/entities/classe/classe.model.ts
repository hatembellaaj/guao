export interface IClasse {
  id: number;
  nomclasse?: string | null;
  nombreeleves?: number | null;
}

export type NewClasse = Omit<IClasse, 'id'> & { id: null };
