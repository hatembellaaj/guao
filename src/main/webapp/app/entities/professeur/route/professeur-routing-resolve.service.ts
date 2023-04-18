import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProfesseur } from '../professeur.model';
import { ProfesseurService } from '../service/professeur.service';

@Injectable({ providedIn: 'root' })
export class ProfesseurRoutingResolveService implements Resolve<IProfesseur | null> {
  constructor(protected service: ProfesseurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProfesseur | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((professeur: HttpResponse<IProfesseur>) => {
          if (professeur.body) {
            return of(professeur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
