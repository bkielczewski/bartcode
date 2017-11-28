import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable'
import { Post } from './post';
import { PostService } from './post.service';

@Injectable()
export class PostResolver implements Resolve<Post> {

  constructor(private postService: PostService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Post> {
    const relativeUrl = '/' + route.params['year'] + '/' + route.params['month'] + '/' + route.params['slug'];
    return this.postService.getPostByRelativeUrl(relativeUrl);
  }

}
