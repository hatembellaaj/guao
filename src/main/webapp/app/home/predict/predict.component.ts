import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PredictService } from '../predict.service';

@Component({
  selector: 'jhi-predict',
  templateUrl: './predict.component.html',
  styleUrls: ['./predict.component.scss'],
})
export class PredictComponent implements OnInit {
  public predictSearchForm!: FormGroup;
  public predictData: any;
  constructor(private formBuilder: FormBuilder, private predictService: PredictService) {}

  ngOnInit(): void {
    this.predictSearchForm = this.formBuilder.group({
      location: [''],
    });
  }

  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  predict(formValues: any) {
    // eslint-disable-next-line no-console
    console.log(formValues);
    this.predictService.predict(formValues.math, formValues.physique).subscribe(data => (this.predictData = data));
    // eslint-disable-next-line no-console
    console.log(this.predictData);
  }
}
