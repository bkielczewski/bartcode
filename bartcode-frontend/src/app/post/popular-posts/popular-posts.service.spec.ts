import { inject, TestBed } from '@angular/core/testing';
import { PopularPostsService } from './popular-posts.service';

describe('PopularPostsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PopularPostsService]
    });
  });

  it('should ...', inject([PopularPostsService], (service: PopularPostsService) => {
    expect(service).toBeTruthy();
  }));
});
