import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Post } from '../post';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Resources } from '../../spring-data-rest';

@Injectable()
export class PopularService {

  constructor(private http: Http) {
  }

  getPopular(): Observable<Post> {
    return this.http.get(environment.serviceUrl + '/posts/search/popular')
      .flatMap((response: Response) => (<Resources<Post>> response.json())._embedded['posts'])
      .publishReplay()
      .refCount();
  }

}
