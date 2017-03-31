import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams } from '@angular/http';

import { Observable } from 'rxjs';

import { Post } from '../post';

import { environment } from '../../../environments/environment';

@Injectable()
export class PostService {

  constructor(private http: Http) {
  }

  getPostByRelativeUrl(relativeUrl: string): Observable<Post> {
    const params: URLSearchParams = new URLSearchParams();
    params.set('relativeUrl', relativeUrl);
    return this.http.get(environment.serviceUrl + '/posts/search/relativeUrl', { search: params })
      .map((response: Response) => (<Post> response.json()));
  }

}
