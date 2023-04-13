import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IInscrption } from '../inscrption.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../inscrption.test-samples';

import { InscrptionService, RestInscrption } from './inscrption.service';

const requireRestSample: RestInscrption = {
  ...sampleWithRequiredData,
  dateinscription: sampleWithRequiredData.dateinscription?.format(DATE_FORMAT),
};

describe('Inscrption Service', () => {
  let service: InscrptionService;
  let httpMock: HttpTestingController;
  let expectedResult: IInscrption | IInscrption[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InscrptionService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Inscrption', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const inscrption = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(inscrption).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Inscrption', () => {
      const inscrption = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(inscrption).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Inscrption', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Inscrption', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Inscrption', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInscrptionToCollectionIfMissing', () => {
      it('should add a Inscrption to an empty array', () => {
        const inscrption: IInscrption = sampleWithRequiredData;
        expectedResult = service.addInscrptionToCollectionIfMissing([], inscrption);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inscrption);
      });

      it('should not add a Inscrption to an array that contains it', () => {
        const inscrption: IInscrption = sampleWithRequiredData;
        const inscrptionCollection: IInscrption[] = [
          {
            ...inscrption,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInscrptionToCollectionIfMissing(inscrptionCollection, inscrption);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Inscrption to an array that doesn't contain it", () => {
        const inscrption: IInscrption = sampleWithRequiredData;
        const inscrptionCollection: IInscrption[] = [sampleWithPartialData];
        expectedResult = service.addInscrptionToCollectionIfMissing(inscrptionCollection, inscrption);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inscrption);
      });

      it('should add only unique Inscrption to an array', () => {
        const inscrptionArray: IInscrption[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const inscrptionCollection: IInscrption[] = [sampleWithRequiredData];
        expectedResult = service.addInscrptionToCollectionIfMissing(inscrptionCollection, ...inscrptionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const inscrption: IInscrption = sampleWithRequiredData;
        const inscrption2: IInscrption = sampleWithPartialData;
        expectedResult = service.addInscrptionToCollectionIfMissing([], inscrption, inscrption2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(inscrption);
        expect(expectedResult).toContain(inscrption2);
      });

      it('should accept null and undefined values', () => {
        const inscrption: IInscrption = sampleWithRequiredData;
        expectedResult = service.addInscrptionToCollectionIfMissing([], null, inscrption, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(inscrption);
      });

      it('should return initial array if no Inscrption is added', () => {
        const inscrptionCollection: IInscrption[] = [sampleWithRequiredData];
        expectedResult = service.addInscrptionToCollectionIfMissing(inscrptionCollection, undefined, null);
        expect(expectedResult).toEqual(inscrptionCollection);
      });
    });

    describe('compareInscrption', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInscrption(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareInscrption(entity1, entity2);
        const compareResult2 = service.compareInscrption(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareInscrption(entity1, entity2);
        const compareResult2 = service.compareInscrption(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareInscrption(entity1, entity2);
        const compareResult2 = service.compareInscrption(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
