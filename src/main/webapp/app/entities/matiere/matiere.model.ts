export interface IMatiere {
  id: number;
  nommatiere?: string | null;
  coefficient?: number | null;
  nombreheure?: number | null;
  nombreexamen?: number | null;
}

export type NewMatiere = Omit<IMatiere, 'id'> & { id: null };
