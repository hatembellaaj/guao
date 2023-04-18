import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ProfesseurFormService } from './professeur-form.service';
import { ProfesseurService } from '../service/professeur.service';
import { IProfesseur } from '../professeur.model';

import { ProfesseurUpdateComponent } from './professeur-update.component';

describe('Professeur Management Update Component', () => {
  let comp: ProfesseurUpdateComponent;
  let fixture: ComponentFixture<ProfesseurUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let professeurFormService: ProfesseurFormService;
  let professeurService: ProfesseurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ProfesseurUpdateComponent],
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
      .overrideTemplate(ProfesseurUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProfesseurUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    professeurFormService = TestBed.inject(ProfesseurFormService);
    professeurService = TestBed.inject(ProfesseurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const professeur: IProfesseur = { id: 456 };

      activatedRoute.data = of({ professeur });
      comp.ngOnInit();

      expect(comp.professeur).toEqual(professeur);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfesseur>>();
      const professeur = { id: 123 };
      jest.spyOn(professeurFormService, 'getProfesseur').mockReturnValue(professeur);
      jest.spyOn(professeurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ professeur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: professeur }));
      saveSubject.complete();

      // THEN
      expect(professeurFormService.getProfesseur).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(professeurService.update).toHaveBeenCalledWith(expect.objectContaining(professeur));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfesseur>>();
      const professeur = { id: 123 };
      jest.spyOn(professeurFormService, 'getProfesseur').mockReturnValue({ id: null });
      jest.spyOn(professeurService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ professeur: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: professeur }));
      saveSubject.complete();

      // THEN
      expect(professeurFormService.getProfesseur).toHaveBeenCalled();
      expect(professeurService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfesseur>>();
      const professeur = { id: 123 };
      jest.spyOn(professeurService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ professeur });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(professeurService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
