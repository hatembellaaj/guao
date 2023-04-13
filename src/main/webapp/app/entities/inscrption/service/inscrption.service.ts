import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInscrption, NewInscrption } from '../inscrption.model';

export type PartialUpdateInscrption = Partial<IInscrption> & Pick<IInscrption, 'id'>;

type RestOf<T extends IInscrption | NewInscrption> = Omit<T, 'dateinscription'> & {
  dateinscription?: string | null;
};

export type RestInscrption = RestOf<IInscrption>;

export type NewRestInscrption = RestOf<NewInscrption>;

export type PartialUpdateRestInscrption = RestOf<PartialUpdateInscrption>;

export type EntityResponseType = HttpResponse<IInscrption>;
export type EntityArrayResponseType = HttpResponse<IInscrption[]>;

@Injectable({ providedIn: 'root' })
export class InscrptionService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/inscrptions');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(inscrption: NewInscrption): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inscrption);
    return this.http
      .post<RestInscrption>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(inscrption: IInscrption): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inscrption);
    return this.http
      .put<RestInscrption>(`${this.resourceUrl}/${this.getInscrptionIdentifier(inscrption)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(inscrption: PartialUpdateInscrption): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(inscrption);
    return this.http
      .patch<RestInscrption>(`${this.resourceUrl}/${this.getInscrptionIdentifier(inscrption)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestInscrption>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestInscrption[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getInscrptionIdentifier(inscrption: Pick<IInscrption, 'id'>): number {
    return inscrption.id;
  }

  compareInscrption(o1: Pick<IInscrption, 'id'> | null, o2: Pick<IInscrption, 'id'> | null): boolean {
    return o1 && o2 ? this.getInscrptionIdentifier(o1) === this.getInscrptionIdentifier(o2) : o1 === o2;
  }

  addInscrptionToCollectionIfMissing<Type extends Pick<IInscrption, 'id'>>(
    inscrptionCollection: Type[],
    ...inscrptionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const inscrptions: Type[] = inscrptionsToCheck.filter(isPresent);
    if (inscrptions.length > 0) {
      const inscrptionCollectionIdentifiers = inscrptionCollection.map(inscrptionItem => this.getInscrptionIdentifier(inscrptionItem)!);
      const inscrptionsToAdd = inscrptions.filter(inscrptionItem => {
        const inscrptionIdentifier = this.getInscrptionIdentifier(inscrptionItem);
        if (inscrptionCollectionIdentifiers.includes(inscrptionIdentifier)) {
          return false;
        }
        inscrptionCollectionIdentifiers.push(inscrptionIdentifier);
        return true;
      });
      return [...inscrptionsToAdd, ...inscrptionCollection];
    }
    return inscrptionCollection;
  }

  protected convertDateFromClient<T extends IInscrption | NewInscrption | PartialUpdateInscrption>(inscrption: T): RestOf<T> {
    return {
      ...inscrption,
      dateinscription: inscrption.dateinscription?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restInscrption: RestInscrption): IInscrption {
    return {
      ...restInscrption,
      dateinscription: restInscrption.dateinscription ? dayjs(restInscrption.dateinscription) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestInscrption>): HttpResponse<IInscrption> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestInscrption[]>): HttpResponse<IInscrption[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
