import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAbsence } from '../absence.model';
import { AbsenceService } from '../service/absence.service';

@Injectable({ providedIn: 'root' })
export class AbsenceRoutingResolveService implements Resolve<IAbsence | null> {
  constructor(protected service: AbsenceService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAbsence | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((absence: HttpResponse<IAbsence>) => {
          if (absence.body) {
            return of(absence.body);
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
