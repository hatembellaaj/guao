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
  public abscenceData: number[] = [0, 0];

  ngOnInit(): void {
    this.absenceService.getCountAbscence().forEach(a => {
      console.log('all=', a);
      this.countAbscence = a;
    });
    this.absenceService.getCountAbscenceJustifie().forEach(a => {
      console.log('justifie=', a);
      this.countAbscenceJustifiees = a;
    });
    this.abscenceData[0] = this.countAbscenceJustifiees;
    this.abscenceData[1] = this.countAbscence - this.countAbscenceJustifiees;

    this.createChart();
    console.log('absecence array : ', this.abscenceData);
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
            data: this.abscenceData,
          },
        ],
      },
      options: {
        aspectRatio: 2.5,
      },
    });
  }
}
