import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { BarChartComponent } from './bar-chart/bar-chart.component';
import { PiechartComponent } from './piechart/piechart.component';

@NgModule({
  imports: [SharedModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent, BarChartComponent, PiechartComponent],
})
export class HomeModule {}
