import { MyserviceService } from './myservice.service';
import { TestBed } from '@angular/core/testing';

describe('MyserviceService', () => {

  let service: MyserviceService;
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        MyserviceService
      ]
    });
    service = TestBed.get(MyserviceService);

  });

  it('should be able to create service instance', () => {
    expect(service).toBeDefined();
  });

});
