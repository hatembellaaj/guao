import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NoteFormService } from './note-form.service';
import { NoteService } from '../service/note.service';
import { INote } from '../note.model';
import { IEleve } from 'app/entities/eleve/eleve.model';
import { EleveService } from 'app/entities/eleve/service/eleve.service';
import { IExamen } from 'app/entities/examen/examen.model';
import { ExamenService } from 'app/entities/examen/service/examen.service';

import { NoteUpdateComponent } from './note-update.component';

describe('Note Management Update Component', () => {
  let comp: NoteUpdateComponent;
  let fixture: ComponentFixture<NoteUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let noteFormService: NoteFormService;
  let noteService: NoteService;
  let eleveService: EleveService;
  let examenService: ExamenService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NoteUpdateComponent],
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
      .overrideTemplate(NoteUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NoteUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    noteFormService = TestBed.inject(NoteFormService);
    noteService = TestBed.inject(NoteService);
    eleveService = TestBed.inject(EleveService);
    examenService = TestBed.inject(ExamenService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Eleve query and add missing value', () => {
      const note: INote = { id: 456 };
      const eleve: IEleve = { id: 60070 };
      note.eleve = eleve;

      const eleveCollection: IEleve[] = [{ id: 94662 }];
      jest.spyOn(eleveService, 'query').mockReturnValue(of(new HttpResponse({ body: eleveCollection })));
      const additionalEleves = [eleve];
      const expectedCollection: IEleve[] = [...additionalEleves, ...eleveCollection];
      jest.spyOn(eleveService, 'addEleveToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ note });
      comp.ngOnInit();

      expect(eleveService.query).toHaveBeenCalled();
      expect(eleveService.addEleveToCollectionIfMissing).toHaveBeenCalledWith(
        eleveCollection,
        ...additionalEleves.map(expect.objectContaining)
      );
      expect(comp.elevesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Examen query and add missing value', () => {
      const note: INote = { id: 456 };
      const examen: IExamen = { id: 79878 };
      note.examen = examen;

      const examenCollection: IExamen[] = [{ id: 17936 }];
      jest.spyOn(examenService, 'query').mockReturnValue(of(new HttpResponse({ body: examenCollection })));
      const additionalExamen = [examen];
      const expectedCollection: IExamen[] = [...additionalExamen, ...examenCollection];
      jest.spyOn(examenService, 'addExamenToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ note });
      comp.ngOnInit();

      expect(examenService.query).toHaveBeenCalled();
      expect(examenService.addExamenToCollectionIfMissing).toHaveBeenCalledWith(
        examenCollection,
        ...additionalExamen.map(expect.objectContaining)
      );
      expect(comp.examenSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const note: INote = { id: 456 };
      const eleve: IEleve = { id: 11223 };
      note.eleve = eleve;
      const examen: IExamen = { id: 75340 };
      note.examen = examen;

      activatedRoute.data = of({ note });
      comp.ngOnInit();

      expect(comp.elevesSharedCollection).toContain(eleve);
      expect(comp.examenSharedCollection).toContain(examen);
      expect(comp.note).toEqual(note);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INote>>();
      const note = { id: 123 };
      jest.spyOn(noteFormService, 'getNote').mockReturnValue(note);
      jest.spyOn(noteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ note });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: note }));
      saveSubject.complete();

      // THEN
      expect(noteFormService.getNote).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(noteService.update).toHaveBeenCalledWith(expect.objectContaining(note));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INote>>();
      const note = { id: 123 };
      jest.spyOn(noteFormService, 'getNote').mockReturnValue({ id: null });
      jest.spyOn(noteService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ note: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: note }));
      saveSubject.complete();

      // THEN
      expect(noteFormService.getNote).toHaveBeenCalled();
      expect(noteService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<INote>>();
      const note = { id: 123 };
      jest.spyOn(noteService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ note });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(noteService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEleve', () => {
      it('Should forward to eleveService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(eleveService, 'compareEleve');
        comp.compareEleve(entity, entity2);
        expect(eleveService.compareEleve).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareExamen', () => {
      it('Should forward to examenService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(examenService, 'compareExamen');
        comp.compareExamen(entity, entity2);
        expect(examenService.compareExamen).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
