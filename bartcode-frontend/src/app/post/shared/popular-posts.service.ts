import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Post } from './post';
import { HalUtils, Resources } from '../../shared/spring-data-rest';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable()
export class PopularPostsService {

  constructor(private http: HttpClient) {
  }

  getPopular(): Observable<Post> {
    return this.http.get<Resources<Post>>(environment.serviceUrl + '/posts/search/popular')
      .flatMap(resources => HalUtils.getEmbedded('posts', resources));
  }

}
