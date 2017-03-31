import { inject, TestBed } from '@angular/core/testing';
import { DatePostCountService } from './date-post-count.service';

describe('DatePostCountService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DatePostCountService]
    });
  });

  it('should ...', inject([DatePostCountService], (service: DatePostCountService) => {
    expect(service).toBeTruthy();
  }));
});
