import { inject, TestBed } from '@angular/core/testing';
import { DocumentService } from './document.service';

describe('DocumentService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DocumentService]
    });
  });

  it('should ...', inject([DocumentService], (service: DocumentService) => {
    expect(service).toBeTruthy();
  }));
});
