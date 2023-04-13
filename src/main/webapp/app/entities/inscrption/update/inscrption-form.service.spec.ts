import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../inscrption.test-samples';

import { InscrptionFormService } from './inscrption-form.service';

describe('Inscrption Form Service', () => {
  let service: InscrptionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InscrptionFormService);
  });

  describe('Service methods', () => {
    describe('createInscrptionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createInscrptionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dateinscription: expect.any(Object),
            commentaire: expect.any(Object),
            tarifinscription: expect.any(Object),
            classe: expect.any(Object),
          })
        );
      });

      it('passing IInscrption should create a new form with FormGroup', () => {
        const formGroup = service.createInscrptionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dateinscription: expect.any(Object),
            commentaire: expect.any(Object),
            tarifinscription: expect.any(Object),
            classe: expect.any(Object),
          })
        );
      });
    });

    describe('getInscrption', () => {
      it('should return NewInscrption for default Inscrption initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createInscrptionFormGroup(sampleWithNewData);

        const inscrption = service.getInscrption(formGroup) as any;

        expect(inscrption).toMatchObject(sampleWithNewData);
      });

      it('should return NewInscrption for empty Inscrption initial value', () => {
        const formGroup = service.createInscrptionFormGroup();

        const inscrption = service.getInscrption(formGroup) as any;

        expect(inscrption).toMatchObject({});
      });

      it('should return IInscrption', () => {
        const formGroup = service.createInscrptionFormGroup(sampleWithRequiredData);

        const inscrption = service.getInscrption(formGroup) as any;

        expect(inscrption).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IInscrption should not enable id FormControl', () => {
        const formGroup = service.createInscrptionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewInscrption should disable id FormControl', () => {
        const formGroup = service.createInscrptionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
