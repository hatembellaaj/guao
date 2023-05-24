import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class PredictService {
  constructor(private http: HttpClient) {}

  // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
  predict(math: any, physique: any) {
    // eslint-disable-next-line @typescript-eslint/restrict-plus-operands
    return this.http.get('http://localhost:5000/predict?math=' + math + '&physique=' + physique);
  }
}
