import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InscrptionComponent } from './list/inscrption.component';
import { InscrptionDetailComponent } from './detail/inscrption-detail.component';
import { InscrptionUpdateComponent } from './update/inscrption-update.component';
import { InscrptionDeleteDialogComponent } from './delete/inscrption-delete-dialog.component';
import { InscrptionRoutingModule } from './route/inscrption-routing.module';

@NgModule({
  imports: [SharedModule, InscrptionRoutingModule],
  declarations: [InscrptionComponent, InscrptionDetailComponent, InscrptionUpdateComponent, InscrptionDeleteDialogComponent],
})
export class InscrptionModule {}
