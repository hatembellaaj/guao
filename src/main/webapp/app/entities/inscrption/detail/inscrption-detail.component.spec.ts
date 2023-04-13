import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { InscrptionDetailComponent } from './inscrption-detail.component';

describe('Inscrption Management Detail Component', () => {
  let comp: InscrptionDetailComponent;
  let fixture: ComponentFixture<InscrptionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InscrptionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ inscrption: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(InscrptionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InscrptionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load inscrption on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.inscrption).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
