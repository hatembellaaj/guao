import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InscrptionComponent } from '../list/inscrption.component';
import { InscrptionDetailComponent } from '../detail/inscrption-detail.component';
import { InscrptionUpdateComponent } from '../update/inscrption-update.component';
import { InscrptionRoutingResolveService } from './inscrption-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const inscrptionRoute: Routes = [
  {
    path: '',
    component: InscrptionComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InscrptionDetailComponent,
    resolve: {
      inscrption: InscrptionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InscrptionUpdateComponent,
    resolve: {
      inscrption: InscrptionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InscrptionUpdateComponent,
    resolve: {
      inscrption: InscrptionRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(inscrptionRoute)],
  exports: [RouterModule],
})
export class InscrptionRoutingModule {}
