import { Component, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { AbsenceService } from 'app/entities/absence/service/absence.service';
import Chart from 'chart.js/auto';
import { forkJoin, Subscription } from 'rxjs';
@Component({
  selector: 'jhi-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss'],
})
export class BarChartComponent implements OnInit, OnDestroy, AfterViewInit {
  public chart: any;
  public countAbscence: number = 0;
  public countAbscenceJustifiees: number = 0;
  public abscenceData: number[] = [0, 0];
  private chartSubscription: Subscription | null = null;

  constructor(protected absenceService: AbsenceService) {}

  ngAfterViewInit(): void {
    setTimeout(() => {
      if (!this.chart) {
        this.createChart();
      }
    });
    console.log(this.abscenceData);
  }

  ngOnInit(): void {
    this.fetchAbsenceCounts();
    this.createChart();
  }
  ngOnDestroy(): void {
    if (this.chartSubscription) {
      this.chartSubscription.unsubscribe();
      if (this.chart) {
        this.chart.destroy();
      }
    }
  }

  fetchAbsenceCounts(): void {
    const countAbscence$ = this.absenceService.getCountAbscence();
    const countAbscenceJustifiees$ = this.absenceService.getCountAbscenceJustifie();
    this.chartSubscription = forkJoin([countAbscence$, countAbscenceJustifiees$]).subscribe(([countAbscence, countAbscenceJustifiees]) => {
      this.countAbscence = countAbscence;
      this.countAbscenceJustifiees = countAbscenceJustifiees;
      this.updateChart();
    });
  }

  updateChart(): void {
    this.abscenceData[0] = this.countAbscenceJustifiees;
    this.abscenceData[1] = this.countAbscence - this.countAbscenceJustifiees;

    if (this.chart) {
      this.chart.data.datasets[0].data = this.abscenceData;
      this.chart.update();
    }
  }

  createChart(): void {
    const canvas = document.getElementById('MyChart') as HTMLCanvasElement;
    const ctx = canvas.getContext('2d');

    if (!ctx) {
      console.error('Failed to get 2D rendering context');
      return;
    }

    this.chart = new Chart(ctx, {
      type: 'pie',
      data: {
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
