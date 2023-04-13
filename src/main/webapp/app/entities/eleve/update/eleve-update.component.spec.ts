import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EleveFormService } from './eleve-form.service';
import { EleveService } from '../service/eleve.service';
import { IEleve } from '../eleve.model';
import { IClasse } from 'app/entities/classe/classe.model';
import { ClasseService } from 'app/entities/classe/service/classe.service';

import { EleveUpdateComponent } from './eleve-update.component';

describe('Eleve Management Update Component', () => {
  let comp: EleveUpdateComponent;
  let fixture: ComponentFixture<EleveUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let eleveFormService: EleveFormService;
  let eleveService: EleveService;
  let classeService: ClasseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EleveUpdateComponent],
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
      .overrideTemplate(EleveUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EleveUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    eleveFormService = TestBed.inject(EleveFormService);
    eleveService = TestBed.inject(EleveService);
    classeService = TestBed.inject(ClasseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Classe query and add missing value', () => {
      const eleve: IEleve = { id: 456 };
      const classe: IClasse = { id: 44322 };
      eleve.classe = classe;

      const classeCollection: IClasse[] = [{ id: 27400 }];
      jest.spyOn(classeService, 'query').mockReturnValue(of(new HttpResponse({ body: classeCollection })));
      const additionalClasses = [classe];
      const expectedCollection: IClasse[] = [...additionalClasses, ...classeCollection];
      jest.spyOn(classeService, 'addClasseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      expect(classeService.query).toHaveBeenCalled();
      expect(classeService.addClasseToCollectionIfMissing).toHaveBeenCalledWith(
        classeCollection,
        ...additionalClasses.map(expect.objectContaining)
      );
      expect(comp.classesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const eleve: IEleve = { id: 456 };
      const classe: IClasse = { id: 38186 };
      eleve.classe = classe;

      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      expect(comp.classesSharedCollection).toContain(classe);
      expect(comp.eleve).toEqual(eleve);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEleve>>();
      const eleve = { id: 123 };
      jest.spyOn(eleveFormService, 'getEleve').mockReturnValue(eleve);
      jest.spyOn(eleveService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eleve }));
      saveSubject.complete();

      // THEN
      expect(eleveFormService.getEleve).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(eleveService.update).toHaveBeenCalledWith(expect.objectContaining(eleve));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEleve>>();
      const eleve = { id: 123 };
      jest.spyOn(eleveFormService, 'getEleve').mockReturnValue({ id: null });
      jest.spyOn(eleveService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eleve: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: eleve }));
      saveSubject.complete();

      // THEN
      expect(eleveFormService.getEleve).toHaveBeenCalled();
      expect(eleveService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEleve>>();
      const eleve = { id: 123 };
      jest.spyOn(eleveService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ eleve });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(eleveService.update).toHaveBeenCalled();
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
