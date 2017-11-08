import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';

import { Observable } from 'rxjs/Observable';

import { PostsService } from './posts.service';
import { Post } from './post';
import { Resources } from '../../shared/spring-data-rest';
import { HttpErrorResponse } from '@angular/common/http';
import { PageableUtils } from '../../shared/pageable';

@Injectable()
export class YearMonthPostsResolver implements Resolve<Resources<Post>> {

  constructor(private postsService: PostsService, private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Resources<Post>> {
    return this.postsService.getPostsByYearMonth(
      route.params['year'], route.params['month'], PageableUtils.fromPage(route.params['page'] - 1, route.params['size']))
      .catch((err: HttpErrorResponse) => {
        this.router.navigate(['/error'], { queryParams: { code: err.status } });
        return Observable.throw(new Error('Couldn\'t get posts by year and month, cause: ' + err));
      });
  }

}
