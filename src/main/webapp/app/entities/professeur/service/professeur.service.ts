import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProfesseur, NewProfesseur } from '../professeur.model';

export type PartialUpdateProfesseur = Partial<IProfesseur> & Pick<IProfesseur, 'id'>;

export type EntityResponseType = HttpResponse<IProfesseur>;
export type EntityArrayResponseType = HttpResponse<IProfesseur[]>;

@Injectable({ providedIn: 'root' })
export class ProfesseurService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/professeurs');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(professeur: NewProfesseur): Observable<EntityResponseType> {
    return this.http.post<IProfesseur>(this.resourceUrl, professeur, { observe: 'response' });
  }

  update(professeur: IProfesseur): Observable<EntityResponseType> {
    return this.http.put<IProfesseur>(`${this.resourceUrl}/${this.getProfesseurIdentifier(professeur)}`, professeur, {
      observe: 'response',
    });
  }

  partialUpdate(professeur: PartialUpdateProfesseur): Observable<EntityResponseType> {
    return this.http.patch<IProfesseur>(`${this.resourceUrl}/${this.getProfesseurIdentifier(professeur)}`, professeur, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfesseur>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfesseur[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getProfesseurIdentifier(professeur: Pick<IProfesseur, 'id'>): number {
    return professeur.id;
  }

  compareProfesseur(o1: Pick<IProfesseur, 'id'> | null, o2: Pick<IProfesseur, 'id'> | null): boolean {
    return o1 && o2 ? this.getProfesseurIdentifier(o1) === this.getProfesseurIdentifier(o2) : o1 === o2;
  }

  addProfesseurToCollectionIfMissing<Type extends Pick<IProfesseur, 'id'>>(
    professeurCollection: Type[],
    ...professeursToCheck: (Type | null | undefined)[]
  ): Type[] {
    const professeurs: Type[] = professeursToCheck.filter(isPresent);
    if (professeurs.length > 0) {
      const professeurCollectionIdentifiers = professeurCollection.map(professeurItem => this.getProfesseurIdentifier(professeurItem)!);
      const professeursToAdd = professeurs.filter(professeurItem => {
        const professeurIdentifier = this.getProfesseurIdentifier(professeurItem);
        if (professeurCollectionIdentifiers.includes(professeurIdentifier)) {
          return false;
        }
        professeurCollectionIdentifiers.push(professeurIdentifier);
        return true;
      });
      return [...professeursToAdd, ...professeurCollection];
    }
    return professeurCollection;
  }
}
