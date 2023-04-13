import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ExamenFormService } from './examen-form.service';
import { ExamenService } from '../service/examen.service';
import { IExamen } from '../examen.model';
import { IMatiere } from 'app/entities/matiere/matiere.model';
import { MatiereService } from 'app/entities/matiere/service/matiere.service';
import { IClasse } from 'app/entities/classe/classe.model';
import { ClasseService } from 'app/entities/classe/service/classe.service';

import { ExamenUpdateComponent } from './examen-update.component';

describe('Examen Management Update Component', () => {
  let comp: ExamenUpdateComponent;
  let fixture: ComponentFixture<ExamenUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let examenFormService: ExamenFormService;
  let examenService: ExamenService;
  let matiereService: MatiereService;
  let classeService: ClasseService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ExamenUpdateComponent],
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
      .overrideTemplate(ExamenUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ExamenUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    examenFormService = TestBed.inject(ExamenFormService);
    examenService = TestBed.inject(ExamenService);
    matiereService = TestBed.inject(MatiereService);
    classeService = TestBed.inject(ClasseService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Matiere query and add missing value', () => {
      const examen: IExamen = { id: 456 };
      const matiere: IMatiere = { id: 83852 };
      examen.matiere = matiere;

      const matiereCollection: IMatiere[] = [{ id: 51021 }];
      jest.spyOn(matiereService, 'query').mockReturnValue(of(new HttpResponse({ body: matiereCollection })));
      const additionalMatieres = [matiere];
      const expectedCollection: IMatiere[] = [...additionalMatieres, ...matiereCollection];
      jest.spyOn(matiereService, 'addMatiereToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ examen });
      comp.ngOnInit();

      expect(matiereService.query).toHaveBeenCalled();
      expect(matiereService.addMatiereToCollectionIfMissing).toHaveBeenCalledWith(
        matiereCollection,
        ...additionalMatieres.map(expect.objectContaining)
      );
      expect(comp.matieresSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Classe query and add missing value', () => {
      const examen: IExamen = { id: 456 };
      const classe: IClasse = { id: 37488 };
      examen.classe = classe;

      const classeCollection: IClasse[] = [{ id: 12323 }];
      jest.spyOn(classeService, 'query').mockReturnValue(of(new HttpResponse({ body: classeCollection })));
      const additionalClasses = [classe];
      const expectedCollection: IClasse[] = [...additionalClasses, ...classeCollection];
      jest.spyOn(classeService, 'addClasseToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ examen });
      comp.ngOnInit();

      expect(classeService.query).toHaveBeenCalled();
      expect(classeService.addClasseToCollectionIfMissing).toHaveBeenCalledWith(
        classeCollection,
        ...additionalClasses.map(expect.objectContaining)
      );
      expect(comp.classesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const examen: IExamen = { id: 456 };
      const matiere: IMatiere = { id: 70983 };
      examen.matiere = matiere;
      const classe: IClasse = { id: 93787 };
      examen.classe = classe;

      activatedRoute.data = of({ examen });
      comp.ngOnInit();

      expect(comp.matieresSharedCollection).toContain(matiere);
      expect(comp.classesSharedCollection).toContain(classe);
      expect(comp.examen).toEqual(examen);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExamen>>();
      const examen = { id: 123 };
      jest.spyOn(examenFormService, 'getExamen').mockReturnValue(examen);
      jest.spyOn(examenService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ examen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: examen }));
      saveSubject.complete();

      // THEN
      expect(examenFormService.getExamen).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(examenService.update).toHaveBeenCalledWith(expect.objectContaining(examen));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExamen>>();
      const examen = { id: 123 };
      jest.spyOn(examenFormService, 'getExamen').mockReturnValue({ id: null });
      jest.spyOn(examenService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ examen: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: examen }));
      saveSubject.complete();

      // THEN
      expect(examenFormService.getExamen).toHaveBeenCalled();
      expect(examenService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExamen>>();
      const examen = { id: 123 };
      jest.spyOn(examenService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ examen });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(examenService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareMatiere', () => {
      it('Should forward to matiereService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(matiereService, 'compareMatiere');
        comp.compareMatiere(entity, entity2);
        expect(matiereService.compareMatiere).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
