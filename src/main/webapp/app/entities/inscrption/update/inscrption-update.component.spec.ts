import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { InscrptionFormService } from './inscrption-form.service';
import { InscrptionService } from '../service/inscrption.service';
import { IInscrption } from '../inscrption.model';
import { IClasse } from 'app/entities/classe/classe.model';
import { ClasseService } from 'app/entities/classe/service/classe.service';

import { InscrptionUpdateComponent } from './inscrption-update.component';

describe('Inscrption Management Update Component', () => {
  let comp: InscrptionUpdateComponent;
  let fixture: ComponentFixture<InscrptionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let inscrptionFormService: InscrptionFormService;
  let inscrptionService: InscrptionService;
  let classeService: ClasseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [InscrptionUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(InscrptionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InscrptionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    inscrptionFormService = TestBed.inject(InscrptionFormService);
    inscrptionService = TestBed.inject(InscrptionService);
    classeService = TestBed.inject(ClasseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Classe query and add missing value', () => {
      const inscrption: IInscrption = { id: 456 };
      const classe: IClasse = { id: 52896 };
      inscrption.classe = classe;

      const classeCollection: IClasse[] = [{ id: 56461 }];
      jest.spyOn(classeService, 'query').mockReturnValue(of(new HttpResponse({ body: classeCollection })));
      const additionalClasses = [classe];
      const expectedCollection: IClasse[] = [...additionalClasses, ...classeCollection];
      jest.spyOn(classeService, 'addClasseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ inscrption });
      comp.ngOnInit();

      expect(classeService.query).toHaveBeenCalled();
      expect(classeService.addClasseToCollectionIfMissing).toHaveBeenCalledWith(
        classeCollection,
        ...additionalClasses.map(expect.objectContaining)
      );
      expect(comp.classesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const inscrption: IInscrption = { id: 456 };
      const classe: IClasse = { id: 84653 };
      inscrption.classe = classe;

      activatedRoute.data = of({ inscrption });
      comp.ngOnInit();

      expect(comp.classesSharedCollection).toContain(classe);
      expect(comp.inscrption).toEqual(inscrption);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInscrption>>();
      const inscrption = { id: 123 };
      jest.spyOn(inscrptionFormService, 'getInscrption').mockReturnValue(inscrption);
      jest.spyOn(inscrptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inscrption });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inscrption }));
      saveSubject.complete();

      // THEN
      expect(inscrptionFormService.getInscrption).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(inscrptionService.update).toHaveBeenCalledWith(expect.objectContaining(inscrption));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInscrption>>();
      const inscrption = { id: 123 };
      jest.spyOn(inscrptionFormService, 'getInscrption').mockReturnValue({ id: null });
      jest.spyOn(inscrptionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inscrption: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: inscrption }));
      saveSubject.complete();

      // THEN
      expect(inscrptionFormService.getInscrption).toHaveBeenCalled();
      expect(inscrptionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IInscrption>>();
      const inscrption = { id: 123 };
      jest.spyOn(inscrptionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ inscrption });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(inscrptionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareClasse', () => {
      it('Should forward to classeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(classeService, 'compareClasse');
        comp.compareClasse(entity, entity2);
        expect(classeService.compareClasse).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
