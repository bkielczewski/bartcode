import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';

import { Observable } from 'rxjs';

import { PostsService } from './posts.service';
import { Post } from './post';
import { Resources } from '../../shared/spring-data-rest';

@Injectable()
export class YearMonthPostsResolver implements Resolve<Resources<Post>> {

  constructor(private postsService: PostsService, private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Resources<Post>> {
    return this.postsService.getPostsByYearMonth(
      route.params['year'], route.params['month'], route.params['page'] - 1, route.params['size'])
      .catch((err: Response) => {
        this.router.navigate(['/error'], { queryParams: { code: err.status } });
        return Observable.throw(new Error('Couldn\'t get posts by year and month, cause: ' + err));
      });
  }

}
