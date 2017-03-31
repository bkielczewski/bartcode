import { inject, TestBed } from '@angular/core/testing';
import { TagPostCountService } from './tag-post-count.service';

describe('TagPostCountService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TagPostCountService]
    });
  });

  it('should ...', inject([TagPostCountService], (service: TagPostCountService) => {
    expect(service).toBeTruthy();
  }));
});
