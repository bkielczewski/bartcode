import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';

import { Observable } from 'rxjs';

import { PostsService } from './posts.service';
import { Post } from '../post';

@Injectable()
export class TagPostsResolver implements Resolve<Post[]> {

  constructor(private postsService: PostsService, private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Post[]> {
    return this.postsService.getPostsByTag(route.params['tag'])
      .toArray()
      .catch((err: Response) => {
        this.router.navigate(['/error'], { queryParams: { code: err.status } });
        return Observable.throw(new Error('Couldn\'t get posts by tag, response: ' + err.statusText));
      });
  }

}
