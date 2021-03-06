import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';

import { Observable } from 'rxjs/Observable';

import { PostsService } from './posts.service';
import { Post } from './post';
import { Resources } from '../../shared/spring-data-rest';
import { PageableUtils } from '../../shared/pageable';

@Injectable()
export class YearMonthPostsResolver implements Resolve<Resources<Post>> {

  constructor(private postsService: PostsService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Resources<Post>> {
    return this.postsService.getPostsByYearMonth(
      route.params['year'], route.params['month'], PageableUtils.fromPage(route.params['page'] - 1, route.params['size']));
  }

}
