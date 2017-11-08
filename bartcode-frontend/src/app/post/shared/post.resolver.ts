import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable'
import { Post } from './post';
import { PostService } from './post.service';
import { NotFoundError } from '../../shared/not-found-error';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable()
export class PostResolver implements Resolve<Post> {

  constructor(private postService: PostService, private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Post> {
    const relativeUrl = '/' + route.params['year'] + '/' + route.params['month'] + '/' + route.params['slug'];
    return this.postService.getPostByRelativeUrl(relativeUrl)
      .catch((err: HttpErrorResponse) => {
        this.router.navigate(['/error'], { queryParams: { code: err.status } });
        return Observable.throw(err.status == 404 ?
          new NotFoundError('Post Not Found, url=' + relativeUrl) :
          new Error('Couldn\'t get post, cause: ' + err));
      });
  }

}
