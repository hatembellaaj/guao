import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInscrption } from '../inscrption.model';
import { InscrptionService } from '../service/inscrption.service';

@Injectable({ providedIn: 'root' })
export class InscrptionRoutingResolveService implements Resolve<IInscrption | null> {
  constructor(protected service: InscrptionService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInscrption | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((inscrption: HttpResponse<IInscrption>) => {
          if (inscrption.body) {
            return of(inscrption.body);
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
