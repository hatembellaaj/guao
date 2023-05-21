import { Component, OnInit } from '@angular/core';
import { AbsenceService } from 'app/entities/absence/service/absence.service';
import Chart from 'chart.js/auto';
@Component({
  selector: 'jhi-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss'],
})
export class BarChartComponent implements OnInit {
  public chart: any;
  public countAbscence: number = 0;
  public countAbscenceJustifiees: number = 0;

  ngOnInit(): void {
    this.absenceService.getCountAbscence().forEach(a => {
      this.countAbscence = a;
    });
    this.absenceService.getCountAbscenceJustifie().forEach(a => {
      this.countAbscenceJustifiees = a;
    });
    this.createChart();
  }

  constructor(protected absenceService: AbsenceService) {}

  createChart(): void {
    this.chart = new Chart('MyChart', {
      type: 'pie', //this denotes tha type of chart

      data: {
        // values on X-Axis
        labels: ['Abscence justifiées', 'Abscence non justifiées'],
        datasets: [
          {
            data: [this.countAbscenceJustifiees, this.countAbscence],
          },
        ],
      },
      options: {
        aspectRatio: 2.5,
      },
    });
  }
}
