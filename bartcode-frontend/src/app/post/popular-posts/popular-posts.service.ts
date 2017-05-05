import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

import { Post } from '../post';
import { Resources } from '../../spring-data-rest';

import { environment } from '../../../environments/environment';

@Injectable()
export class PopularPostsService {

  constructor(private http: Http) {
  }

  getPopular(): Observable<Post> {
    return this.http.get(environment.serviceUrl + '/posts/search/popular')
      .flatMap((response: Response) => (<Resources<Post>> response.json())._embedded['posts']);
  }

}
